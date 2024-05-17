package com.group.goyaapp.service

import com.group.goyaapp.domain.Quest
import com.group.goyaapp.domain.QuestState
import com.group.goyaapp.dto.request.quest.QuestAcceptRequest
import com.group.goyaapp.dto.request.quest.QuestClearRequest
import com.group.goyaapp.dto.request.quest.QuestLoadRequest
import com.group.goyaapp.dto.response.QuestResponse
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.util.readQuestDataStream
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
		val questData = readQuestDataStream("questData.json")!!.first { it.questId == request.quest_id }
		val preQuestUserInfo = questRepository.findByUserUidAndQuestId(request.user_uid, questData.preQuest)
		val quest = questRepository.findByUserUidAndQuestId(request.user_uid, request.quest_id) ?: Quest(
			request.user_uid, request.quest_id
		)
		
		// TODO :user 정보에 위치정보 map 추가
		/*if(questData.questMapId != user.mapId)
		{
			// 현재 있는 맵과 다르면 실패
			return
		}*/
		
		requireNotNull(userRepository.findById(request.user_uid)) { "유저 정보를 찾을 수 없습니다." }
		requireNotNull(quest) { "퀘스트 정보를 찾을 수 없습니다." }
		require(preQuestUserInfo?.state == QuestState.FINISHED || questData.preQuest == "x") { "선행 퀘스트를 클리어하지 않았습니다." }
		require(quest.state == QuestState.AVAILABLE) { "퀘스트를 수락할 수 없는 상태입니다." }
		
		quest.state = QuestState.ACCOMPLISHING
		questRepository.save(quest)
		
		return QuestResponse.of(quest)
	}
	
	/**
	 * 퀘스트 클리어
	 */
	@Transactional
	fun clearQuest(request: QuestClearRequest): QuestResponse {
		val quest = questRepository.findByUserUidAndQuestId(request.user_uid, request.quest_id)
		requireNotNull(quest) { "퀘스트 정보를 찾을 수 없습니다." }
		require(quest.state == QuestState.ACCOMPLISHING) { "퀘스트를 클리어할 수 없는 상태입니다." }
		
		quest.state = QuestState.FINISHED
		questRepository.save(quest)
		
		return QuestResponse.of(quest)
	}
	
	/**
	 * 유저 퀘스트 리스트를 불러온다.
	 */
	@Transactional
	fun loadQuestList(request: QuestLoadRequest): List<QuestResponse> {
		val questDataList = readQuestDataStream("questData.json")
		val userQuestList = questRepository.findByUserUid(request.user_uid)
		return questDataList!!.map { questData ->
			val quest = Quest(request.user_uid, questData.questId)
			if (userQuestList != null) {
				val quest2 = userQuestList.filter { userQuest -> userQuest.questId == questData.questId }
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