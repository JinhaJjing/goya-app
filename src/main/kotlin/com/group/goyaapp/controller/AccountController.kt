package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.account.AccountCreateRequest
import com.group.goyaapp.dto.request.account.AccountDeleteRequest
import com.group.goyaapp.dto.request.account.AccountUpdateRequest
import com.group.goyaapp.dto.response.AccountResponse
import com.group.goyaapp.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
	private val accountService: AccountService,
) {
	
	@PostMapping("/account/signup")
	fun saveAccount(
		@RequestBody
		request: AccountCreateRequest
	) {
		accountService.saveAccount(request)
	}
	
	@GetMapping("/account")
	fun getAccountList(): List<AccountResponse> {
		return accountService.getAccountAll()
	}
	
	@PostMapping("/account/withdrawal")
	fun deleteAccount(
		@RequestBody
		request: AccountDeleteRequest
	) {
		accountService.deleteAccount(request)
	}
	
	@PostMapping("/account/login")
	fun login(
		@RequestBody
		request: AccountUpdateRequest
	): AccountResponse {
		accountService.updateAccountLogin(request)
		return accountService.getAccountInfo(request.id)
	}
	
	@PostMapping("/account/logout")
	fun logout(
		@RequestBody
		request: AccountUpdateRequest
	) {
		accountService.updateAccountLogout(request)
	}
}