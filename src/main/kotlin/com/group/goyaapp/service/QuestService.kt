package com.group.goyaapp.service

import com.google.common.reflect.TypeToken
import com.group.goyaapp.domain.Quest
import com.group.goyaapp.domain.User
import com.group.goyaapp.domain.enumType.QuestState
import com.group.goyaapp.dto.data.QuestData
import com.group.goyaapp.dto.request.quest.QuestAcceptRequest
import com.group.goyaapp.dto.request.quest.QuestClearRequest
import com.group.goyaapp.dto.request.quest.QuestLoadRequest
import com.group.goyaapp.dto.response.QuestResponse
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.util.getServerDateTime
import com.group.goyaapp.util.readDataFromFile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
	private val userRepository: UserRepository,
	private val questRepository: QuestRepository,
) {
	/**
	 * 퀘스트 수락
	 */
	@Transactional
	fun acceptQuest(request: QuestAcceptRequest): QuestResponse {
		val questDataList: ArrayList<QuestData>? =
			readDataFromFile("questData.json", object : TypeToken<ArrayList<QuestData>?>() {})
		val user = userRepository.findByUserUid(request.userUid)
		val questData = questDataList!!.first { it.QuestID == request.questId }
		if (questData.PreQuest != "x") {
			val preQuestUserInfo = questRepository.findByUserUidAndQuestId(request.userUid, questData.PreQuest)
			requireNotNull(preQuestUserInfo) { "선행 퀘스트 정보를 찾을 수 없습니다." }
			require(preQuestUserInfo.state == QuestState.FINISHED) { "선행 퀘스트를 클리어하지 않았습니다." }
		}
		val curQuestUserInfo = questRepository.findByUserUidAndQuestId(request.userUid, request.questId) ?: Quest(
			request.userUid, request.questId, QuestState.AVAILABLE
		)
		requireNotNull(user) { "유저 정보를 찾을 수 없습니다." }
		require(user.curMap == questData.QuestMapID) { "퀘스트를 수락할 수 없는 맵입니다." }
		require(curQuestUserInfo.state == QuestState.AVAILABLE) { "퀘스트를 수락할 수 없는 상태입니다." }
		
		curQuestUserInfo.state = QuestState.ACCOMPLISHING
		questRepository.save(curQuestUserInfo)
		
		// 퀘스트 시작 액션 처리
		if (questData.StartAction2.startsWith("Tp")) {
			val mapIdNum = questData.StartAction1.substring(2)
			val tpMapId = "Ma_$mapIdNum"
			teleportMap(user, tpMapId)
		}
		
		return QuestResponse.of(curQuestUserInfo)
	}
	
	/**
	 * 퀘스트 클리어
	 */
	@Transactional
	fun clearQuest(request: QuestClearRequest): QuestResponse {
		val questDataList: ArrayList<QuestData>? =
			readDataFromFile("questData.json", object : TypeToken<ArrayList<QuestData>?>() {})
		val questData = questDataList!!.first { it.QuestID == request.questId }
		val curQuestUserInfo = questRepository.findByUserUidAndQuestId(request.userUid, request.questId)
		val user = userRepository.findByUserUid(request.userUid)
		
		requireNotNull(curQuestUserInfo) { "유저 퀘스트 수락 이력을 찾을 수 없습니다." }
		require(curQuestUserInfo.state == QuestState.ACCOMPLISHING) { "퀘스트를 클리어할 수 없는 상태입니다." }
		require(curQuestUserInfo.count >= questData.MissionCount) { "퀘스트 클리어 조건을 만족하지 못했습니다." }
		
		requireNotNull(user) { "유저 정보를 찾을 수 없습니다." }
		require(user.curMap == questData.QuestMapID) { "퀘스트를 수락할 수 없는 맵입니다." }
		require(curQuestUserInfo.state == QuestState.AVAILABLE) { "퀘스트를 수락할 수 없는 상태입니다." }
		
		curQuestUserInfo.state = QuestState.FINISHED
		questRepository.save(curQuestUserInfo)
		
		// 퀘스트 완료 액션 처리
		if (questData.EndAction2.startsWith("Tp")) {
			val mapIdNum = questData.StartAction1.substring(2)
			val tpMapId = "Ma_$mapIdNum"
			teleportMap(user, tpMapId)
		}
		
		return QuestResponse.of(curQuestUserInfo)
	}
	
	/**
	 * 유저 퀘스트 리스트를 불러온다.
	 */
	@Transactional
	fun loadQuestList(request: QuestLoadRequest): List<QuestResponse> {
		val user = userRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		val questDataList: ArrayList<QuestData>? =
			readDataFromFile("questData.json", object : TypeToken<ArrayList<QuestData>?>() {})
		
		// 퀘스트 상태 업데이트
		updateQuestState(request.userUid)
		
		val userQuestList = questRepository.findByUserUid(user.userUid)
		return questDataList!!.map { questData ->
			val quest = Quest(user.userUid, questData.QuestID)
			if (userQuestList != null) {
				val quest2 = userQuestList.filter { userQuest -> userQuest.questId == questData.QuestID }
				if (quest2.isNotEmpty()) {
					quest.state = quest2.first().state
					quest.count = quest2.first().count
				}
			}
			
			// 수락 가능한 상태
			if (quest.state == QuestState.UNAVAILABLE) {
				if (questData.PreQuest != "x") {
					val preQuestUserInfo = questRepository.findByUserUidAndQuestId(request.userUid, questData.PreQuest)
					if (preQuestUserInfo != null && preQuestUserInfo.state == QuestState.FINISHED) {
						quest.state = QuestState.AVAILABLE
					}
				}
				else {
					quest.state = QuestState.AVAILABLE
				}
			}
			
			// 현재 맵과 같은지 확인
			if (user.curMap != questData.QuestMapID) {
				quest.state = QuestState.UNAVAILABLE
			}
			
			QuestResponse.of(quest)
		}
	}
	
	/**
	 * 유저의 퀘스트 상태를 업데이트한다.
	 */
	fun updateQuestState(userUid: Long) {
		val questDataList: ArrayList<QuestData>? =
			readDataFromFile("questData.json", object : TypeToken<ArrayList<QuestData>?>() {})
		
		questRepository.findByUserUid(userUid)?.map { userQuest ->
			// count가 만족했으면 달성 상태로 변경
			val questData = questDataList!!.first { it.QuestID == userQuest.questId }
			if (userQuest.state == QuestState.ACCOMPLISHING && userQuest.count >= questData.MissionCount) {
				userQuest.state = QuestState.COMPLETED
				questRepository.save(userQuest)
			}
		}
	}
	
	/**
	 * 맵 이동
	 */
	fun teleportMap(user: User, mapId: String) {
		user.curMap = mapId
		userRepository.save(user)
	}
	
	fun resetQuest(
		request: QuestLoadRequest
	): List<QuestResponse> {
		val userQuestList = questRepository.findByUserUid(request.userUid)
		userQuestList?.map { userQuest ->
			userQuest.state = QuestState.UNAVAILABLE
			userQuest.count = 0
			userQuest.datetimeMod = getServerDateTime()
			questRepository.save(userQuest)
		}
		
		return loadQuestList(request)
	}
}