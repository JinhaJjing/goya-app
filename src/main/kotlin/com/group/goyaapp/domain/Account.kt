package com.group.goyaapp.domain

import com.group.goyaapp.util.getServerDateTime
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Account(
	@Column(name = "id", unique = true)
	var accountId: String,
	
	@Column(name = "pw")
	var accountPW: String,
	
	@Column(name = "datetime_add")
	var datetimeAdd: LocalDateTime? = getServerDateTime(),
	
	@Column(name = "datetime_mod")
	var datetimeMod: LocalDateTime? = getServerDateTime(),
	
	@Column(name = "datetime_last_login")
	var datetimeLastLogin: LocalDateTime? = getServerDateTime(),
	
	@Id
	@Column(name = "user_uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val userUid: Long? = null,
) {
	constructor() : this("", "")
}