package com.group.goyaapp.service.account

import com.group.goyaapp.domain.Account
import com.group.goyaapp.dto.request.account.AccountCreateRequest
import com.group.goyaapp.dto.request.account.AccountDeleteRequest
import com.group.goyaapp.repository.AccountRepository
import com.group.goyaapp.service.AccountService
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountServiceTest @Autowired constructor(
	private val accountRepository: AccountRepository
) {
	
	@Autowired
	private lateinit var accountService: AccountService
	
	@AfterEach
	fun clean() {
		accountRepository.deleteAll()
	}
	
	@Test
	@DisplayName("계정 저장이 정상 동작한다")
	fun saveAccountTest() {        // given
		val request = AccountCreateRequest("진하찡", "1234")
		
		// when
		accountService.saveAccount(request)
		
		// then
		val results = accountRepository.findAll()
		assertThat(results).hasSize(1)
		assertThat(results[0].accountId).isEqualTo("얄루진하찡")
		assertThat(results[0].accountPW).isEqualTo("1234")
	}
	
	@Test
	@DisplayName("계정 조회가 정상 동작한다")
	fun getAccountAllTest() {        // given
		accountRepository.saveAll(
			listOf(
				Account("얄루진하찡", "1234"),
				Account("얄루진하찡2", "1234"),
			)
		)
		
		// when
		val results = accountService.getAccountAll()
		
		// then
		assertThat(results).hasSize(2)
		assertThat(results).extracting("accountId").containsExactlyInAnyOrder("얄루진하찡", "얄루진하찡2")
		assertThat(results).extracting("accountPW").containsExactlyInAnyOrder("1234", "1234")
	}
	
	@Test
	@DisplayName("계정 삭제가 정상 동작한다")
	fun deleteUserTest() {        // given
		accountRepository.save(Account("JinhaJjing", "1234"))
		
		// when
		accountService.deleteAccount(AccountDeleteRequest("JinhaJjing", "1234"))
		
		// then
		assertThat(accountRepository.findAll()).isEmpty()
	}
}