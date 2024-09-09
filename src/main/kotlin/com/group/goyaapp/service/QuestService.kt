package com.group.goyaapp.service

import com.group.goyaapp.domain.Quest
import com.group.goyaapp.domain.Quest2
import com.group.goyaapp.domain.User
import com.group.goyaapp.domain.enumType.QuestState
import com.group.goyaapp.dto.request.quest.QuestAcceptRequest
import com.group.goyaapp.dto.request.quest.QuestActionRequest
import com.group.goyaapp.dto.request.quest.QuestClearRequest
import com.group.goyaapp.dto.request.quest.QuestLoadRequest
import com.group.goyaapp.dto.response.QuestResponse
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.QuestRepository2
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.util.getQuestData
import com.group.goyaapp.util.getQuestData2
import com.group.goyaapp.util.getServerDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
	private val userRepository: UserRepository,
	private val questRepository: QuestRepository,
	private val questRepository2: QuestRepository2,
) {
	/**
	 * 퀘스트 수락
	 */
	@Transactional
	fun acceptQuest(request: QuestAcceptRequest): List<QuestResponse> {
		// 퀘스트 상태 업데이트
		updateQuestState(request.userUid)
		
		val user = userRepository.findByUserUid(request.userUid)
		val questData = getQuestData().first { it.QuestID == request.questId }
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
		when (curQuestUserInfo.state) {
			QuestState.UNAVAILABLE -> throw Exception("아직 퀘스트를 수락할 수 없습니다.")
			QuestState.ACCOMPLISHING -> throw Exception("이미 진행중인 퀘스트입니다.")
			QuestState.FINISHED, QuestState.COMPLETED -> throw Exception("이미 완료된 퀘스트입니다.")
		}
		
		curQuestUserInfo.state = QuestState.ACCOMPLISHING
		questRepository.save(curQuestUserInfo)
		
		// 퀘스트 시작 액션 처리
		if (questData.StartAction2.startsWith("Tp")) {
			val mapIdNum = questData.StartAction2.substring(questData.StartAction2.length - 4)
			val tpMapId = "Ma_$mapIdNum"
			teleportMap(user, tpMapId)
		}
		
		return loadQuestList(QuestLoadRequest(request.userUid))
	}
	
	/**
	 * 퀘스트 수락(ver 2)
	 */
	@Transactional
	fun acceptQuest2(request: QuestAcceptRequest): List<QuestResponse> {
		val user = userRepository.findByUserUid(request.userUid)
		val questData2 = getQuestData2().first { it.QuestID == request.questId }
		val curQuestUserInfo = questRepository2.findByUserUidAndQuestId(request.userUid, request.questId) ?: Quest2(
			request.userUid, request.questId, QuestState.AVAILABLE
		)
		requireNotNull(user) { "유저 정보를 찾을 수 없습니다." }
		require(request.questId == questData2.QuestID) { "퀘스트 정보를 찾을 수 없습니다." }
		when (curQuestUserInfo.state) {
			QuestState.ACCOMPLISHING -> throw Exception("이미 진행중인 퀘스트입니다.")
			QuestState.FINISHED, QuestState.COMPLETED -> throw Exception("이미 완료된 퀘스트입니다.")
		}
		
		curQuestUserInfo.state = QuestState.ACCOMPLISHING
		questRepository2.save(curQuestUserInfo)
		
		return loadQuestList2(QuestLoadRequest(request.userUid))
	}
	
	/**
	 * 퀘스트 클리어
	 */
	@Transactional
	fun clearQuest(request: QuestClearRequest): List<QuestResponse> {
		val questData = getQuestData().first { it.QuestID == request.questId }
		val curQuestUserInfo = questRepository.findByUserUidAndQuestId(request.userUid, request.questId)
		val user = userRepository.findByUserUid(request.userUid)
		
		requireNotNull(user) { "유저 정보를 찾을 수 없습니다." }
		requireNotNull(curQuestUserInfo) { "유저 퀘스트 수락 이력을 찾을 수 없습니다." }
		require(user.curMap == questData.QuestMapID) { "퀘스트를 클리어할 수 없는 맵입니다." }
		require(curQuestUserInfo.count >= questData.MissionCount) { "퀘스트 클리어 조건을 만족하지 못했습니다." }
		when (curQuestUserInfo.state) {
			QuestState.UNAVAILABLE, QuestState.AVAILABLE -> throw Exception("아직 퀘스트를 수락할 수 없습니다.")
			QuestState.ACCOMPLISHING -> throw Exception("아직 진행중인 퀘스트입니다.")
			QuestState.FINISHED -> throw Exception("이미 완료된 퀘스트입니다.")
		}
		
		
		curQuestUserInfo.state = QuestState.FINISHED
		questRepository.save(curQuestUserInfo)
		
		// 퀘스트 완료 액션 처리
		if (questData.EndAction2.startsWith("Tp")) {
			val mapIdNum = questData.StartAction2.substring(questData.StartAction2.length - 4)
			val tpMapId = "Ma_$mapIdNum"
			teleportMap(user, tpMapId)
		}
		
		return loadQuestList(QuestLoadRequest(request.userUid))
	}
	
	/**
	 * 퀘스트 클리어 ver2
	 * 퀘스트 클리어 시 다음 퀘스트 자동 수락
	 */
	@Transactional
	fun clearQuest2(request: QuestClearRequest): List<QuestResponse> {
		val questData2 = getQuestData2().first { it.QuestID == request.questId }
		var curQuestUserInfo = questRepository2.findByUserUidAndQuestId(request.userUid, request.questId)
		val user = userRepository.findByUserUid(request.userUid) ?: throw Exception("유저 정보를 찾을 수 없습니다.")
		
		if (curQuestUserInfo == null && questData2.QuestID != "Qu_0000") throw Exception("유저 퀘스트 수락 이력을 찾을 수 없습니다.")
		curQuestUserInfo = curQuestUserInfo ?: Quest2(request.userUid, questData2.QuestID, QuestState.ACCOMPLISHING)
		
		if (curQuestUserInfo.state == QuestState.UNAVAILABLE || curQuestUserInfo.state == QuestState.AVAILABLE || curQuestUserInfo.state == QuestState.FINISHED) throw Exception(
			"진행중인 퀘스트가 아닙니다."
		)
		
		curQuestUserInfo.state = QuestState.FINISHED
		questRepository2.save(curQuestUserInfo)
		
		// 다음 퀘스트 자동 수락
		val nextQuestData = getQuestData2().getOrNull(getQuestData2().indexOf(questData2) + 1)
		if (nextQuestData != null || nextQuestData?.QuestID == "") {
			val nextQuestUserInfo = questRepository2.findByUserUidAndQuestId(request.userUid, nextQuestData.QuestID)
			nextQuestUserInfo?.state = QuestState.ACCOMPLISHING
			questRepository2.save(
				nextQuestUserInfo ?: Quest2(
					request.userUid, nextQuestData.QuestID, QuestState.ACCOMPLISHING
				)
			)
		}
		
		return loadQuestList2(QuestLoadRequest(request.userUid))
	}
	
	/**
	 * 유저 퀘스트 리스트를 불러온다.
	 */
	@Transactional
	fun loadQuestList(request: QuestLoadRequest): List<QuestResponse> {
		val user = userRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		
		// 퀘스트 상태 업데이트
		updateQuestState(request.userUid)
		
		val userQuestList = questRepository.findByUserUid(user.userUid)
		return getQuestData().map { questData ->
			val quest = Quest(user.userUid, questData.QuestID)
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
	 * 유저 퀘스트 정보 조회 ver2
	 */
	@Transactional
	fun loadQuestList2(request: QuestLoadRequest): List<QuestResponse> {
		val user = userRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		
		val userQuestList = questRepository2.findByUserUid(user.userUid)
		return getQuestData2().map { questData ->
			val quest = Quest(user.userUid, questData.QuestID)
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
	 * 유저의 퀘스트 상태를 업데이트한다.
	 */
	fun updateQuestState(userUid: Long) {
		questRepository.findByUserUid(userUid)?.map { userQuest ->
			// count가 만족했으면 달성 상태로 변경
			val questData = getQuestData().first { it.QuestID == userQuest.questId }
			
			if (userQuest.state == QuestState.ACCOMPLISHING) {
				// MissionCondition 이 Quest인 경우 그 퀘스트 깨면 Complete 처리
				if (questData.MissionCondition == "Quest") {
					questRepository.findByUserUidAndQuestId(userUid, questData.MissionTarget)?.let {
						if (it.state == QuestState.FINISHED) {
							userQuest.state = QuestState.COMPLETED
						}
					}
				}
				// 그 외 경우 // TODO
				else {
					if (userQuest.count >= questData.MissionCount) {
						userQuest.state = QuestState.COMPLETED
					}
				}
			}
			else if (userQuest.state == QuestState.UNAVAILABLE) {
				// 조건 확인 후 수락 가능한 상태로 변경
				if (questData.PreQuest != "x") {
					val preQuestUserInfo = questRepository.findByUserUidAndQuestId(userUid, questData.PreQuest)
					if (preQuestUserInfo != null && preQuestUserInfo.state == QuestState.FINISHED) {
						userQuest.state = QuestState.AVAILABLE
					}
				}
				else {
					userQuest.state = QuestState.AVAILABLE
				}
			}
			
			questRepository.save(userQuest)
		}
	}
	
	/**
	 * 맵 이동
	 */
	fun teleportMap(user: User, mapId: String) {
		user.curMap = mapId
		userRepository.save(user)
	}
	
	/**
	 * 퀘스트 초기화
	 */
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
	
	/**
	 * 퀘스트 초기화 ver2
	 */
	fun resetQuest2(
		request: QuestLoadRequest
	): List<QuestResponse> {
		val userQuestList = questRepository2.findByUserUid(request.userUid)
		userQuestList?.map { userQuest ->
			userQuest.state = QuestState.AVAILABLE
			userQuest.count = 0
			userQuest.datetimeMod = getServerDateTime()
			questRepository2.save(userQuest)
		}
		
		return loadQuestList2(request)
	}
	
	/**
	 * 퀘스트 조건에 해당하는 액션 시 카운트를 추가합니다
	 */
	fun questAction(request: QuestActionRequest): List<QuestResponse> {
		getQuestData().map {
			if (it.MissionCondition == request.type && it.MissionTarget == request.target) {
				val userQuest = questRepository.findByUserUidAndQuestId(request.userUid, it.QuestID)
				
				// 산예 미니게임의 경우 스토리 진행 중에도 지속 참여 가능하다
				if (request.type == "Game" && request.target == "Np_0002") {
					if ((userQuest != null && (userQuest.state == QuestState.ACCOMPLISHING || userQuest.state == QuestState.COMPLETED || userQuest.state == QuestState.FINISHED))) {
						// 최고 점수로 저장한다
						userQuest.count = Math.max(request.count, userQuest.count)
						questRepository.save(userQuest)
					}
				}
				else if (userQuest != null && userQuest.state == QuestState.ACCOMPLISHING) {
					userQuest.count = (request.count + userQuest.count).coerceAtMost(it.MissionCount)
					questRepository.save(userQuest)
				}
			}
		}
		
		return loadQuestList(QuestLoadRequest(request.userUid))
	}
}