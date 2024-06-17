package com.group.goyaapp.domain

import com.group.goyaapp.domain.enumType.QuestState
import com.group.goyaapp.util.getServerDateTime
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Quest(
	
	@Column(name = "user_uid")
	var userUid: Long,
	
	@Column(name = "quest_id")
	var questId: String,
	
	@Enumerated(EnumType.STRING)
	var state: QuestState = QuestState.AVAILABLE,
	
	@Column(name = "count")
	var count: Int = 0,
	
	@Column(name = "datetime_add")
	var datetimeAdd: LocalDateTime? = getServerDateTime(),
	
	@Column(name = "datetime_mod")
	var datetimeMod: LocalDateTime? = getServerDateTime(),
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val rid: Long? = null,
) {
	constructor() : this(0, "")
}