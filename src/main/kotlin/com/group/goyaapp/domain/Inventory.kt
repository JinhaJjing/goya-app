package com.group.goyaapp.domain

import com.group.goyaapp.util.getServerDateTime
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
	
	@Column(name = "datetime_add")
	var datetimeAdd: LocalDateTime? = getServerDateTime(),
	
	@Column(name = "datetime_mod")
	var datetimeMod: LocalDateTime? = getServerDateTime(),
	
	@Id
	@Column(name = "rid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val rid: Long? = null,
) {
	constructor() : this(0, "", 0)
}