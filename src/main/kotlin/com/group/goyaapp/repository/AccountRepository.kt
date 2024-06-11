package com.group.goyaapp.repository

import com.group.goyaapp.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, String> {
	
	fun findByAccountIdAndAccountPW(id: String, pw: String): Account?
	fun findByAccountId(id: String): Account?
	fun findByUserUid(userUid: Long): Account?
}