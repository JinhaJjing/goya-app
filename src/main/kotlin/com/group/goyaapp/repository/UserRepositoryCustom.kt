package com.group.goyaapp.repository

import com.group.goyaapp.domain.User

interface UserRepositoryCustom {

  fun findAllWithHistories(): List<User>

}