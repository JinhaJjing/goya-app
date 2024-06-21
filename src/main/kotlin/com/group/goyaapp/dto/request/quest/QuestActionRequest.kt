package com.group.goyaapp.dto.request.quest

data class QuestActionRequest(
	val userUid: Long, val type: String, val target: String, val count: Int
)
