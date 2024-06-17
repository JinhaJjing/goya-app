package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.quest.QuestAcceptRequest
import com.group.goyaapp.dto.request.quest.QuestClearRequest
import com.group.goyaapp.dto.request.quest.QuestLoadRequest
import com.group.goyaapp.dto.response.DefaultRes
import com.group.goyaapp.dto.response.QuestResponse
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
	): DefaultRes<List<QuestResponse>> {
		try {
			val result = questService.loadQuestList(request)
			return DefaultRes.res(StatusCode.CREATED, ResponseMessage.QUEST_INFO_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "퀘스트 클리어", description = "특정 퀘스트를 클리어합니다.")
	@PostMapping("/quest/clear")
	fun clearQuest(
		@RequestBody
		request: QuestClearRequest
	): DefaultRes<QuestResponse> {
		try {
			val result = questService.clearQuest(request)
			return DefaultRes.res(StatusCode.CREATED, ResponseMessage.QUEST_CLEAR_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "퀘스트 수락", description = "특정 퀘스트를 수락합니다.")
	@PostMapping("/quest/accept")
	fun acceptQuest(
		@RequestBody
		request: QuestAcceptRequest
	): DefaultRes<QuestResponse> {
		
		try {
			val result = questService.acceptQuest(request)
			return DefaultRes.res(StatusCode.CREATED, ResponseMessage.QUEST_ACCEPT_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
}