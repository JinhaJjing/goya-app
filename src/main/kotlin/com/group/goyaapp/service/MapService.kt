package com.group.goyaapp.service

import com.group.goyaapp.domain.enumType.QuestState
import com.group.goyaapp.dto.request.map.MapEnterRequest
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.util.fail
import com.group.goyaapp.util.readMapDataStream
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MapService(
    val questRepository: QuestRepository,
    val userRepository: UserRepository,
) {
    
    @Transactional
    fun mapEnter(request: MapEnterRequest): UserResponse {
        val mapData = readMapDataStream("questData.json")!!
        val mapInfo = mapData.first { it.mapID == request.mapId }
        val userQuestInfo = questRepository.findByUserUidAndQuestId(request.userUid, mapInfo.unlockCondition) ?: fail()
        if (userQuestInfo.state != QuestState.FINISHED) fail()
        val user = userRepository.findById(request.userUid) ?: fail()
        user.updateUserCurMap(request.mapId)
        return userRepository.save(user).let { UserResponse.of(it) }
    }
}