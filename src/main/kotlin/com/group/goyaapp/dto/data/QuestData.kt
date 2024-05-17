package com.group.goyaapp.dto.data

data class QuestData(
	val questId: String,
	val questType: Int,
	val questMapId: String,
	val preQuest: String,
	val questGiveUp: Int,
	val startNPC: String,
	val startAction1: String,
	val startAction2: String,
	val startAction3: String,
	val missionCondition: String,
	val missionTarget: String,
	val missionCount: Int,
	val endNPC: String,
	val endAction1: String,
	val endAction2: String,
	val endAction3: String,
)