package com.group.goyaapp.service

import com.google.common.reflect.TypeToken
import com.group.goyaapp.domain.Quest
import com.group.goyaapp.domain.enumType.QuestState
import com.group.goyaapp.dto.data.QuestData
import com.group.goyaapp.dto.request.quest.QuestAcceptRequest
import com.group.goyaapp.dto.request.quest.QuestClearRequest
import com.group.goyaapp.dto.request.quest.QuestLoadRequest
import com.group.goyaapp.dto.response.QuestResponse
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.UserRepository
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
		val questData = questDataList!!.first { it.QuestID == request.questId }
		if (questData.PreQuest != "x") {
			val preQuestUserInfo = questRepository.findByUserUidAndQuestId(request.userUid, questData.PreQuest)
			requireNotNull(preQuestUserInfo) { "선행 퀘스트 정보를 찾을 수 없습니다." }
			require(preQuestUserInfo.state == QuestState.FINISHED) { "선행 퀘스트를 클리어하지 않았습니다." }
		}
		val curQuestUserInfo = questRepository.findByUserUidAndQuestId(request.userUid, request.questId) ?: Quest(
			request.userUid, request.questId
		)
		val user = userRepository.findById(request.userUid)
		requireNotNull(user) { "유저 정보를 찾을 수 없습니다." }
		require(user.curMap == questData.QuestMapID) { "퀘스트를 수락할 수 없는 맵입니다." }
		require(curQuestUserInfo.state == QuestState.AVAILABLE) { "퀘스트를 수락할 수 없는 상태입니다." }
		
		curQuestUserInfo.state = QuestState.ACCOMPLISHING
		questRepository.save(curQuestUserInfo)
		
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
		requireNotNull(curQuestUserInfo) { "유저 퀘스트 수락 이력을 찾을 수 없습니다." }
		require(curQuestUserInfo.state == QuestState.ACCOMPLISHING) { "퀘스트를 클리어할 수 없는 상태입니다." }
		require(curQuestUserInfo.count >= questData.MissionCount) { "퀘스트 클리어 조건을 만족하지 못했습니다." }
		curQuestUserInfo.state = QuestState.FINISHED
		questRepository.save(curQuestUserInfo)
		
		return QuestResponse.of(curQuestUserInfo)
	}
	
	/**
	 * 유저 퀘스트 리스트를 불러온다.
	 */
	@Transactional
	fun loadQuestList(request: QuestLoadRequest): List<QuestResponse> {
		val userQuestList = questRepository.findByUserUid(request.user_uid)
		val questDataList: ArrayList<QuestData>? =
			readDataFromFile("questData.json", object : TypeToken<ArrayList<QuestData>?>() {})
		return questDataList!!.map { questData ->
			val quest = Quest(request.user_uid, questData.QuestID)
			if (userQuestList != null) {
				val quest2 = userQuestList.filter { userQuest -> userQuest.questId == questData.QuestID }
				if (quest2.isNotEmpty()) {
					quest.state = quest2.first().state
					quest.count = quest2.first().count
				}
			}
			QuestResponse.of(quest)
		}
	}
	
	/**
	 * 유저의 클리어된 퀘스트 수를 반환한다.
	 */
	@Transactional
	fun checkSpiritCount(request: QuestLoadRequest): Int {
		return questRepository.findByUserUid(request.user_uid)?.count { it.state == QuestState.FINISHED } ?: 0
	}
}