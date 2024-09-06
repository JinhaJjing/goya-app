package com.group.goyaapp.repository

import com.group.goyaapp.domain.Quest2
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository2 : JpaRepository<Quest2, Long> {
	
	fun findByUserUidAndQuestId(userUid: Long, questId: String): Quest2?
	fun findByUserUid(userUid: Long): List<Quest2>?
	
}