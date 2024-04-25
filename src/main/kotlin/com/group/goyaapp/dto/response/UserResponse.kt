package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.User

data class UserResponse(
  val userUid: Long,
  val nickname: String,
  val level: Int?,
  val exp: Int?,
  val savepoint: Int?,
) {

  companion object {
    fun of(user: User): UserResponse {
      return UserResponse(
        userUid = user.id!!,
        nickname = user.nickname,
        level = user.level,
        exp = user.exp,
        savepoint = user.savepoint
      )
    }
  }

}