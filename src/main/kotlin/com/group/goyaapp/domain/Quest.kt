package com.group.goyaapp.domain

import com.group.goyaapp.domain.enumType.QuestState
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Quest(
	
	@Column(name = "user_uid")
	var userUid: Int,
	
	@Column(name = "quest_id")
	var questId: String,
	
	@Enumerated(EnumType.STRING)
	var state: QuestState = QuestState.AVAILABLE,
	
	@Column(name = "count")
	var count: Int = 0,
	
	@Column(name = "datetime_add")
	var datetimeAdd: LocalDateTime? = LocalDateTime.now(),
	
	@Column(name = "datetime_mod")
	var datetimeMod: LocalDateTime? = LocalDateTime.now(),
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val rid: Int? = null,
) {
	constructor() : this(0, "")
}