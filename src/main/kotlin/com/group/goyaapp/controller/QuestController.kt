package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.quest.QuestAcceptRequest
import com.group.goyaapp.dto.request.quest.QuestActionRequest
import com.group.goyaapp.dto.request.quest.QuestClearRequest
import com.group.goyaapp.dto.request.quest.QuestLoadRequest
import com.group.goyaapp.dto.response.DefaultRes
import com.group.goyaapp.dto.response.ResponseMessage
import com.group.goyaapp.dto.response.StatusCode
import com.group.goyaapp.service.QuestService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class QuestController(
	private val questService: QuestService,
) {
	@Operation(summary = "퀘스트 정보 조회", description = "퀘스트 정보를 조회합니다.")
	@PostMapping("/quest/info")
	fun loadQuestList(
		@RequestBody
		request: QuestLoadRequest
	): DefaultRes<out Any> {
		try {
			val result = questService.loadQuestList(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.QUEST_INFO_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "퀘스트 정보 조회", description = "퀘스트 정보를 조회합니다.")
	@PostMapping("/quest/info2")
	fun loadQuestList2(
		@RequestBody
		request: QuestLoadRequest
	): DefaultRes<out Any> {
		try {
			val result = questService.loadQuestList2(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.QUEST_INFO_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "퀘스트 클리어", description = "특정 퀘스트를 클리어합니다.")
	@PostMapping("/quest/clear")
	fun clearQuest(
		@RequestBody
		request: QuestClearRequest
	): DefaultRes<out Any> {
		try {
			val result = questService.clearQuest(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.QUEST_CLEAR_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "퀘스트 클리어 ver2", description = "퀘스트 정보를 조회합니다.")
	@PostMapping("/quest/clear2")
	fun clearQuest2(
		@RequestBody
		request: QuestClearRequest
	): DefaultRes<out Any> {
		try {
			val result = questService.clearQuest2(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.QUEST_CLEAR_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "퀘스트 수락", description = "특정 퀘스트를 수락합니다.")
	@PostMapping("/quest/accept")
	fun acceptQuest(
		@RequestBody
		request: QuestAcceptRequest
	): DefaultRes<out Any> {
		
		try {
			val result = questService.acceptQuest(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.QUEST_ACCEPT_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "퀘스트 수락2", description = "특정 퀘스트를 수락합니다.")
	@PostMapping("/quest/accept2")
	fun acceptQuest2(
		@RequestBody
		request: QuestAcceptRequest
	): DefaultRes<out Any> {
		
		try {
			val result = questService.acceptQuest2(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.QUEST_ACCEPT_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(
		summary = "퀘스트 액션",
		description = "퀘스트 액션을 수행합니다.\n" + "\n" + "type : Game → 리듬게임🎵 / target : 리듬게임대상(Np_0002) / count : 점수\n" + "\n" + "type : Dialog → NPC 대화🙌 / target : 대화대상(Np_0001) / count : 대화 횟수\n" + "\n" + "type : Item → 아이템 수집🎁 / target : 수집대상(It_0001) / count : 획득 아이템 갯수\n" + "\n" + "type : Monster → 몬스터 사냥🎃 / target : 처치대상(Ms_0001) / count : 몬스터 처치수\n" + "\n"
	)
	@PostMapping("/quest/action")
	fun actionQuest(
		@RequestBody
		request: QuestActionRequest
	): DefaultRes<out Any> {
		try {
			val result = questService.questAction(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.QUEST_ACTION_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "퀘스트 초기화(치트용)", description = "유저의 퀘스트 상태를 초기화합니다.")
	@PostMapping("/quest/reset")
	fun resetQuest(
		@RequestBody
		request: QuestLoadRequest
	): DefaultRes<out Any> {
		try {
			val result = questService.resetQuest(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.QUEST_INFO_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
}