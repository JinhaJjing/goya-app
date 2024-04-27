package com.group.goyaapp.domain

import javax.persistence.*

@Entity
class GameMap(
    val userUid: Int,
    val mapId: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}