package com.group.goyaapp.repository

import com.group.goyaapp.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
	fun findByNickname(name: String): User?
	fun findByUserUid(rid: Long): User?
}