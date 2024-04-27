package com.group.goyaapp.repository

import com.group.goyaapp.domain.GameMap
import org.springframework.data.jpa.repository.JpaRepository

interface MapRepository: JpaRepository<GameMap, Long> {

    fun findByMapIdAndUserUid(mapId: Int, userUid: Int): GameMap?

}