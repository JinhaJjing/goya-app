package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.Quest
import com.group.goyaapp.domain.QuestState

data class QuestResponse(
	val questId: String,
	val state: QuestState,
	val count: Int,
) {
	companion object {
		fun of(quest: Quest): QuestResponse {
			return QuestResponse(
				questId = quest.questId, state = quest.state, count = quest.count
			)
		}
	}
}