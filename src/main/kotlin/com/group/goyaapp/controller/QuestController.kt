package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.quest.QuestAcceptRequest
import com.group.goyaapp.dto.request.quest.QuestClearRequest
import com.group.goyaapp.dto.request.quest.QuestLoadRequest
import com.group.goyaapp.dto.response.QuestResponse
import com.group.goyaapp.service.QuestService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class QuestController(
	private val questService: QuestService,
) {
	@PostMapping("/quest/info")
	fun loadQuestList(
		@RequestBody
		request: QuestLoadRequest
	): List<QuestResponse> {
		return questService.loadQuestList(request)
	}
	
	@PostMapping("/quest/clear")
	fun clearQuest(
		@RequestBody
		request: QuestClearRequest
	): QuestResponse {
		return questService.clearQuest(request)
	}
	
	@PostMapping("/quest/accept")
	fun acceptQuest(
		@RequestBody
		request: QuestAcceptRequest
	): QuestResponse {
		return questService.acceptQuest(request)
	}
}