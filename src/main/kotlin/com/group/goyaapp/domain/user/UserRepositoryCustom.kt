package com.group.goyaapp.domain.user

interface UserRepositoryCustom {

  fun findAllWithHistories(): List<User>

}