package com.group.goyaapp.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Account(
	
	@Column(name = "id", unique = true)
	var accountId: String,
	
	@Column(name = "pw")
	var accountPW: String,
	
	@Column(name = "datetime_add")
	var datetimeAdd: LocalDateTime? = LocalDateTime.now(),
	
	@Column(name = "datetime_mod")
	var datetimeMod: LocalDateTime? = LocalDateTime.now(),
	
	@Column(name = "datetime_last_login")
	var datetimeLastLogin: LocalDateTime? = LocalDateTime.MIN,
	
	@Id
	@Column(name = "user_uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val user_uid: Long? = null,
	
	//@OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
	//val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
) {
	
	init {
		if (accountId.isBlank()) {
			throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
		}
		if (accountPW.isBlank()) {
			throw IllegalArgumentException("비밀번호는 비어 있을 수 없습니다")
		}
	}
}