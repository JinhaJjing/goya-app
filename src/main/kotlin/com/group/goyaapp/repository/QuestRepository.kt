package com.group.goyaapp.repository

import com.group.goyaapp.domain.Quest
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository : JpaRepository<Quest, Long> {
	
	fun findByUserUidAndQuestId(userUid: Int, questId: String): Quest?
	fun findByUserUid(userUid: Int): List<Quest>?
	
}