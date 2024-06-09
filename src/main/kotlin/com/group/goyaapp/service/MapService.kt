package com.group.goyaapp.service

import com.google.common.reflect.TypeToken
import com.group.goyaapp.domain.enumType.QuestState
import com.group.goyaapp.dto.data.MapData
import com.group.goyaapp.dto.request.map.MapEnterRequest
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.util.failInputArgument
import com.group.goyaapp.util.readDataFromFile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MapService(
	val questRepository: QuestRepository,
	val userRepository: UserRepository,
) {
	
	@Transactional
	fun mapEnter(request: MapEnterRequest): UserResponse {
		val mapDataList: ArrayList<MapData>? =
			readDataFromFile("mapData.json", object : TypeToken<ArrayList<MapData>?>() {})
		val mapInfo = mapDataList!!.first { it.MapID == request.mapId }
		val userQuestInfo = questRepository.findByUserUidAndQuestId(request.userUid, mapInfo.UnlockCondition) ?: failInputArgument()
		if (userQuestInfo.state != QuestState.FINISHED) failInputArgument()
		val user = userRepository.findById(request.userUid) ?: failInputArgument()
		user.updateUserCurMap(request.mapId)
		return userRepository.save(user).let { UserResponse.of(it) }
	}
}