package com.group.goyaapp.service

import com.group.goyaapp.domain.Account
import com.group.goyaapp.dto.request.AccountCreateRequest
import com.group.goyaapp.dto.request.AccountDeleteRequest
import com.group.goyaapp.dto.request.AccountUpdateRequest
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
	fun saveAccount(request: AccountCreateRequest) {
		val newUser = Account(request.id, request.pw)
		val account = accountRepository.findByAccountId(request.id)
		if (account != null) fail()
		newUser.datetimeAdd = LocalDateTime.now()
		newUser.datetimeMod = LocalDateTime.now()
		accountRepository.save(newUser)
	}
	
	@Transactional(readOnly = true)
	fun getAccountAll(): List<AccountResponse> {
		return accountRepository.findAll().map { account -> AccountResponse.of(account) }
	}
	
	@Transactional
	fun updateAccountLogin(request: AccountUpdateRequest): AccountResponse {
		val account = accountRepository.findByIdOrThrow(request.id)
		if (account.accountPW != request.pw) fail()
		account.datetimeMod = LocalDateTime.now()
		account.datetimeLastLogin = LocalDateTime.now()
		return accountRepository.findByAccountId(request.id).let { AccountResponse.of(it!!) }
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
	}/*
        @Transactional(readOnly = true)
        fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
            return accountRepository.findAllWithHistories()
                .map(UserLoanHistoryResponse::of)
        }
    */
	
}