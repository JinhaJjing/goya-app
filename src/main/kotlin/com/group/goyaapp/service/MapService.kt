package com.group.goyaapp.service

import com.group.goyaapp.domain.GameMap
import com.group.goyaapp.dto.request.map.MapEnterRequest
import com.group.goyaapp.repository.MapRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MapService(
    private val mapRepository: MapRepository,
) {

    @Transactional
    fun mapEnter(request: MapEnterRequest) {
        val map = mapRepository.findByMapIdAndUserUid(request.map_id, request.user_uid)
        // map.datetimeMod = LocalDateTime.now()
        if (map != null){
            mapRepository.save(GameMap(request.map_id, request.user_uid))
        }
    }
}