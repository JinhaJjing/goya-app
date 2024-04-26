package com.group.goyaapp.repository

import com.group.goyaapp.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
	
	fun findByNickname(name: String): User?
	fun findById(userUid: Int): User?
	
}