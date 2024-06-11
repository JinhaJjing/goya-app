package com.group.goyaapp.repository

import com.group.goyaapp.domain.Quest
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository : JpaRepository<Quest, Long> {
	
	fun findByUserUidAndQuestId(userUid: Long, questId: String): Quest?
	fun findByUserUid(userUid: Long): List<Quest>?
	
}