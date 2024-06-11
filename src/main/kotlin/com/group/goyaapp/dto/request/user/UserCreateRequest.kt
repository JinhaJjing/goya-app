package com.group.goyaapp.dto.request.user

data class UserCreateRequest(
  val userUid: Long,
  val nickname: String,
)