package com.group.goyaapp.service

import com.group.goyaapp.domain.Quest
import com.group.goyaapp.domain.User
import com.group.goyaapp.domain.enumType.QuestState
import com.group.goyaapp.dto.request.quest.QuestAcceptRequest
import com.group.goyaapp.dto.request.quest.QuestClearRequest
import com.group.goyaapp.dto.request.quest.QuestLoadRequest
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.UserRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuestServiceTest @Autowired constructor(
	private val questRepository: QuestRepository,
	private val userRepository: UserRepository,
) {
	
	@Autowired
	private lateinit var questService: QuestService
	
	@AfterEach
	fun clean() {
		questRepository.deleteAll()
	}
	
	@Test
	@DisplayName("유저 퀘스트 조회가 정상 동작한다")
	fun loadQuestCommonTest1() {        // given
		val request = QuestLoadRequest(1)
		
		// when
		val results = questService.loadQuestList(request)
		
		// then
		// TODO : 퀘스트 데이터를 미리 넣어두어야 함
		assertThat(results).isEmpty()
	}
	
	@Test
	@DisplayName("퀘스트 수락이 정상 동작한다")
	fun acceptQuestTest() {        // given
		userRepository.save(User(1))
		
		// when
		questService.acceptQuest(QuestAcceptRequest(1, "Qu_0001"))
		
		// then
		val results = questRepository.findAll()
		assertThat(results).hasSize(1)
		assertThat(results[0].userUid).isEqualTo(1)
		assertThat(results[0].questId).isEqualTo("Qu_0001")
		assertThat(results[0].state).isEqualTo(QuestState.ACCOMPLISHING)
	}
	
	@Test
	@DisplayName("퀘스트 클리어가 정상 동작한다")
	fun clearQuestTest() {        // given
		questRepository.save(
			Quest(1, "Qu_0001", QuestState.ACCOMPLISHING, 1)
		)
		
		// when
		questService.clearQuest(QuestClearRequest(1, "Qu_0001"))
		
		// then
		val results = questRepository.findAll()
		assertThat(results).hasSize(1)
		assertThat(results[0].userUid).isEqualTo(1)
		assertThat(results[0].questId).isEqualTo("Qu_0001")
		assertThat(results[0].state).isEqualTo(QuestState.FINISHED)
	}
}