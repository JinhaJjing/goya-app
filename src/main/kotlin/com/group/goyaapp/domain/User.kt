package com.group.goyaapp.domain

import jakarta.persistence.*;

@Table(name = "users")
@Entity
class User(
	
	@Column(name = "nickname")
	var nickname: String,
	
	@Column(name = "level")
	var level: Int = 0, // deprecated
	
	@Column(name = "exp")
	var exp: Int = 0, // deprecated
	
	@Column(name = "cur_map")
	var curMap: String = "Ma_0001",
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Int? = null,
) {
	constructor() : this("")
	
	init {
		/*
		if (nickname.isBlank()) {
			throw IllegalArgumentException("닉네임은 비어 있을 수 없습니다")
		}*/
		this.nickname = ""
		this.level = 0
		this.exp = 0
		this.curMap = "Ma_0001"
	}
	
	fun updateNickName(name: String) {
		this.nickname = name
	}
	
	fun updateUserCurMap(map: String) {
		this.curMap = map
	}
}