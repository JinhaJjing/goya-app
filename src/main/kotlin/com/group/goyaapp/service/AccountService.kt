package com.group.goyaapp.service

import com.group.goyaapp.domain.Account
import com.group.goyaapp.dto.request.account.AccountCreateRequest
import com.group.goyaapp.dto.request.account.AccountDeleteRequest
import com.group.goyaapp.dto.request.account.AccountUpdateRequest
import com.group.goyaapp.dto.response.AccountResponse
import com.group.goyaapp.repository.AccountRepository
import com.group.goyaapp.util.fail
import com.group.goyaapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AccountService(
	private val accountRepository: AccountRepository,
) {
	
	@Transactional
	fun saveAccount(request: AccountCreateRequest): AccountResponse {
		val newUser = Account(request.id, request.pw)
		val account = accountRepository.findByAccountId(request.id)
		if (account != null) fail()
		newUser.datetimeAdd = LocalDateTime.now()
		newUser.datetimeMod = LocalDateTime.now()
		return accountRepository.save(newUser).let { AccountResponse.of(it) }
	}
	
	@Transactional(readOnly = true)
	fun getAccountAll(): List<AccountResponse> {
		return accountRepository.findAll().map { account -> AccountResponse.of(account) }
	}
	
	@Transactional
	fun getAccountInfo(accountId: String): AccountResponse {
		val a = accountRepository.findByAccountId(accountId) ?: fail()
		return a.let { AccountResponse.of(it) }
	}
	
	@Transactional
	fun updateAccountLogin(request: AccountUpdateRequest): AccountResponse { // TODO 로그인으로 바꾸기
		val account = accountRepository.findByAccountIdAndAccountPW(request.id, request.pw) ?: fail()
		account.datetimeMod = LocalDateTime.now()
		account.datetimeLastLogin = LocalDateTime.now()
		return accountRepository.findByAccountId(account.accountId).let { AccountResponse.of(it!!) }
	}
	
	@Transactional
	fun updateAccountLogout(request: AccountUpdateRequest): AccountResponse {
		val account = accountRepository.findByIdOrThrow(request.id)
		account.datetimeMod = LocalDateTime.now()
		account.datetimeLastLogin = LocalDateTime.now()
		return accountRepository.findByAccountId(request.id).let { AccountResponse.of(it!!) }
	}
	
	@Transactional
	fun deleteAccount(request: AccountDeleteRequest) {
		val account = accountRepository.findByAccountIdAndAccountPW(
			request.id, request.pw
		) ?: fail()
		accountRepository.delete(account)
	}
}