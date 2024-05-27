package com.group.goyaapp.service

import com.group.goyaapp.domain.Account
import com.group.goyaapp.dto.request.account.AccountCreateRequest
import com.group.goyaapp.dto.request.account.AccountDeleteRequest
import com.group.goyaapp.dto.request.account.AccountUpdateRequest
import com.group.goyaapp.repository.AccountRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
	fun saveAccountTest() {
		// given
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
	fun getAccountAllTest() {
		// given
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
	fun deleteUserTest() {
		// given
		accountRepository.save(Account("JinhaJjing", "1234"))
		
		// when
		accountService.deleteAccount(AccountDeleteRequest("JinhaJjing", "1234"))
		
		// then
		assertThat(accountRepository.findAll()).isEmpty()
	}
	
	@Test
	@DisplayName("Save account with unique id")
	fun shouldSaveAccountWithUniqueId() {
		// given
		val request = AccountCreateRequest("UniqueID", "1234")
		
		// when
		accountService.saveAccount(request)
		
		// then
		val savedAccount = accountRepository.findByAccountId("UniqueID")
		assertThat(savedAccount).isNotNull()
	}
	
	@Test
	@DisplayName("Fail to save account with duplicate id")
	fun shouldFailToSaveAccountWithDuplicateId() {
		// given
		accountRepository.save(Account("DuplicateID", "1234"))
		val request = AccountCreateRequest("DuplicateID", "1234")
		
		// when
		val exception = assertThrows<Exception> {
			accountService.saveAccount(request)
		}
		
		// then
		assertThat(exception.message).isEqualTo("계정 생성에 실패하였습니다.")
	}
	
	@Test
	@DisplayName("Update account login with valid id and password")
	fun shouldUpdateAccountLoginWithValidIdAndPassword() {
		// given
		val savedAccount = accountRepository.save(Account("ValidID", "1234"))
		val request = AccountUpdateRequest(savedAccount.accountId, savedAccount.accountPW)
		
		// when
		accountService.updateAccountLogin(request)
		
		// then
		val updatedAccount = accountRepository.findByAccountId(savedAccount.accountId)
		assertThat(updatedAccount?.datetimeLastLogin).isNotNull()
	}
	
	@Test
	@DisplayName("Fail to update account login with invalid id or password")
	fun shouldFailToUpdateAccountLoginWithInvalidIdOrPassword() {
		// given
		val savedAccount = accountRepository.save(Account("ValidID", "1234"))
		val request = AccountUpdateRequest(savedAccount.accountId, "WrongPassword")
		
		// when
		val exception = assertThrows<Exception> {
			accountService.updateAccountLogin(request)
		}
		
		// then
		assertThat(exception.message).isEqualTo("계정 조회에 실패하였습니다.")
	}
	
	@Test
	@DisplayName("Delete existing account with valid id and password")
	fun shouldDeleteExistingAccountWithValidIdAndPassword() {
		// given
		val savedAccount = accountRepository.save(Account("ToBeDeleted", "1234"))
		
		// when
		accountService.deleteAccount(AccountDeleteRequest(savedAccount.accountId, savedAccount.accountPW))
		
		// then
		assertThat(accountRepository.findByAccountId(savedAccount.accountId)).isNull()
	}
	
	@Test
	@DisplayName("Fail to delete non-existing account or invalid password")
	fun shouldFailToDeleteNonExistingAccountOrInvalidPassword() {
		// when
		val exception = assertThrows<Exception> {
			accountService.deleteAccount(AccountDeleteRequest("NonExistingID", "1234"))
		}
		
		// then
		assertThat(exception.message).isEqualTo("계정 삭제에 실패하였습니다.")
	}
}