package com.group.goyaapp.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Inventory(
	
	@Column(name = "user_uid")
	var userUid: Long,
	
	@Column(name = "item_id")
	var itemId: String,
	
	@Column(name = "item_count")
	var itemCount: Long,
	
	@Id
	@Column(name = "rid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val rid: Long? = null,
) {
	@Column(name = "datetime_add")
	var datetimeAdd: LocalDateTime? = LocalDateTime.now()
	
	@Column(name = "datetime_mod")
	var datetimeMod: LocalDateTime? = LocalDateTime.now()
	
	init {
		this.datetimeAdd = LocalDateTime.now()
		this.datetimeMod = LocalDateTime.now()
	}
}