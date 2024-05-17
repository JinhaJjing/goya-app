package com.group.goyaapp.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Quest constructor(
	
	@Column(name = "user_uid")
	var userUid: Int,
	
	@Column(name = "quest_id")
	var questId: String,
	
	@Enumerated(EnumType.STRING)
	var state: QuestState = QuestState.NOT_AVAILABLE,
	
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
	
}