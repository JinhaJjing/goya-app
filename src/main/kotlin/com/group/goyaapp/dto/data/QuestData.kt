package com.group.goyaapp.dto.data

data class QuestData(
	val QuestID: String = "",
	val QuestType: Int = 0,
	val QuestMapID: String = "",
	val PreQuest: String = "",
	val QuestGiveUp: Int = 0,
	val StartNPC: String = "",
	val StartAction1: String = "",
	val StartAction2: String = "",
	val StartAction3: String = "",
	val MissionCondition: String = "",
	val MissionTarget: String = "",
	val MissionCount: Int = 0,
	val EndNPC: String = "",
	val EndAction1: String = "",
	val EndAction2: String = "",
	val EndAction3: String = "",
)