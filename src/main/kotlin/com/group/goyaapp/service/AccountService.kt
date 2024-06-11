package com.group.goyaapp.service

import com.group.goyaapp.domain.Account
import com.group.goyaapp.dto.request.account.AccountCreateRequest
import com.group.goyaapp.dto.request.account.AccountDeleteRequest
import com.group.goyaapp.dto.request.account.AccountLogoutRequest
import com.group.goyaapp.dto.request.account.AccountUpdateRequest
import com.group.goyaapp.dto.response.AccountResponse
import com.group.goyaapp.repository.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AccountService(
	private val accountRepository: AccountRepository,
) {
	@Transactional
	fun saveAccount(request: AccountCreateRequest): AccountResponse {
		if (request.id.isBlank() || request.pw.isBlank()) throw Exception("아이디와 비밀번호를 입력해주세요.")
		val newUser = Account(request.id, request.pw)
		val account = accountRepository.findByAccountId(request.id)
		if (account != null) throw Exception("이미 존재하는 아이디입니다.")
		newUser.datetimeAdd = LocalDateTime.now()
		newUser.datetimeMod = LocalDateTime.now()
		return accountRepository.save(newUser).let { AccountResponse.of(it) }
	}
	
	@Transactional(readOnly = true)
	fun getAccountAll(): List<AccountResponse> {
		return accountRepository.findAll().map { account -> AccountResponse.of(account) }
	}
	
	@Transactional
	fun updateAccountLogin(request: AccountUpdateRequest): AccountResponse {
		val account =
			accountRepository.findByAccountIdAndAccountPW(request.id, request.pw) ?: throw Exception("계정이 존재하지 않습니다.")
		account.datetimeMod = LocalDateTime.now()
		account.datetimeLastLogin = LocalDateTime.now()
		accountRepository.save(account)
		return accountRepository.findByAccountId(account.accountId).let { AccountResponse.of(it!!) }
	}
	
	@Transactional
	fun updateAccountLogout(request: AccountLogoutRequest): Account {
		val account = accountRepository.findByUserUid(request.userUid) ?: throw Exception("계정이 존재하지 않습니다.")
		account.datetimeMod = LocalDateTime.now()
		account.datetimeLastLogin = LocalDateTime.now()
		return accountRepository.save(account)
	}
	
	@Transactional
	fun deleteAccount(request: AccountDeleteRequest) {
		val account = accountRepository.findByAccountIdAndAccountPW(
			request.id, request.pw
		) ?: throw Exception("계정이 존재하지 않습니다.")
		accountRepository.delete(account)
	}
}