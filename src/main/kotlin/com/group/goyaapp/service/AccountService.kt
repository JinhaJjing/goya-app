package com.group.goyaapp.service

import com.group.goyaapp.domain.Account
import com.group.goyaapp.dto.request.account.AccountCreateRequest
import com.group.goyaapp.dto.request.account.AccountDeleteRequest
import com.group.goyaapp.dto.request.account.AccountLogoutRequest
import com.group.goyaapp.dto.request.account.AccountUpdateRequest
import com.group.goyaapp.dto.response.AccountResponse
import com.group.goyaapp.repository.AccountRepository
import com.group.goyaapp.util.getServerDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
	private val accountRepository: AccountRepository,
) {
	@Transactional
	fun saveAccount(request: AccountCreateRequest): AccountResponse {
		val reqId = request.id.trim()
		val reqPw = request.pw.trim()
		if (reqId.isBlank() || reqPw.isBlank()) throw Exception("아이디와 비밀번호를 입력해주세요.")
		if (reqId.length < 4 || reqId.length > 12) throw Exception("아이디는 4자 이상 12자 이하로 입력해주세요.")
		if (reqPw.length < 4 || reqPw.length > 12) throw Exception("비밀번호는 4자 이상 12자 이하로 입력해주세요.")
		if (!reqId.matches(Regex("^[a-zA-Z0-9ㄱ-ㅣ가-힣]*$"))) throw Exception("아이디는 한글, 영어, 숫자만 입력해주세요.")
		if (!reqPw.matches(Regex("^[a-zA-Z0-9ㄱ-ㅣ가-힣]*$"))) throw Exception("비밀번호는 한글, 영어, 숫자만 입력해주세요.")
		
		val newUser = Account(reqId, reqPw)
		val account = accountRepository.findByAccountId(reqId)
		if (account != null) throw Exception("이미 존재하는 아이디입니다.")
		return accountRepository.save(newUser).let { AccountResponse.of(it) }
	}
	
	@Transactional(readOnly = true)
	fun getAccountAll(): List<AccountResponse> {
		return accountRepository.findAll().map { account -> AccountResponse.of(account) }
	}
	
	@Transactional
	fun updateAccountLogin(request: AccountUpdateRequest): AccountResponse {
		val reqId = request.id.trim()
		val reqPw = request.pw.trim()
		val account = accountRepository.findByAccountIdAndAccountPW(reqId, reqPw) ?: throw Exception("계정이 존재하지 않습니다.")
		if (account.accountId != reqId || account.accountPW != reqPw) throw Exception("아이디나 비밀번호가 일치하지 않습니다.")
		account.datetimeLastLogin = getServerDateTime()
		accountRepository.save(account)
		return accountRepository.findByAccountId(account.accountId).let { AccountResponse.of(it!!) }
	}
	
	@Transactional
	fun updateAccountLogout(request: AccountLogoutRequest): Account {
		val account = accountRepository.findByUserUid(request.userUid) ?: throw Exception("계정이 존재하지 않습니다.")
		account.datetimeMod = getServerDateTime()
		account.datetimeLastLogin = getServerDateTime()
		return accountRepository.save(account)
	}
	
	@Transactional
	fun deleteAccount(request: AccountDeleteRequest) {
		val reqId = request.id.trim()
		val reqPw = request.pw.trim()
		val account = accountRepository.findByAccountIdAndAccountPW(
			reqId, reqPw
		) ?: throw Exception("계정이 존재하지 않습니다.")
		accountRepository.delete(account)
	}
	
	@Transactional
	fun saveGuestAccount(): AccountResponse {
		var id: String
		do {
			id = "guestId" + (100000..999999).random().toString()
		} while (accountRepository.findByAccountId(id) != null)
		val pw: String = "guestPw" + (100000..999999).random().toString()
		return accountRepository.save(Account(id, pw)).let { AccountResponse.of(it) }
	}
}