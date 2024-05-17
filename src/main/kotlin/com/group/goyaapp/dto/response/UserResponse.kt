package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.User

data class UserResponse(
	val userUid: Int,
	val nickname: String,
	val level: Int,
	val exp: Int,
	val savedMap: String,
) {
	
	companion object {
		fun of(user: User): UserResponse {
			return UserResponse(
				userUid = user.id!!,
				nickname = user.nickname,
				level = user.level,
				exp = user.exp,
				savedMap = user.savedMap
			)
		}
	}
	
}