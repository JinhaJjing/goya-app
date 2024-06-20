package com.group.goyaapp.service

import com.google.common.reflect.TypeToken
import com.group.goyaapp.domain.enumType.QuestState
import com.group.goyaapp.dto.data.MapData
import com.group.goyaapp.dto.request.map.MapEnterRequest
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.UserRepository
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
		val user = userRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		val mapDataList: ArrayList<MapData>? =
			readDataFromFile("mapData.json", object : TypeToken<ArrayList<MapData>?>() {})
		
		val mapInfo = mapDataList!!.first { it.MapID == request.mapId }
		if (mapInfo.UnlockCondition != "x") {
			val userQuestInfo =
				questRepository.findByUserUidAndQuestId(request.userUid, mapInfo.UnlockCondition)
				?: throw Exception("맵 진입하기 위한 퀘스트가 완료되지 않았습니다.")
			if (userQuestInfo.state != QuestState.FINISHED) throw Exception("맵이 열려있지 않습니다.")
		}
		user.updateUserCurMap(request.mapId)
		return userRepository.save(user).let { UserResponse.of(it) }
	}
}