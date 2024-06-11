package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.account.AccountCreateRequest
import com.group.goyaapp.dto.request.account.AccountDeleteRequest
import com.group.goyaapp.dto.request.account.AccountLogoutRequest
import com.group.goyaapp.dto.request.account.AccountUpdateRequest
import com.group.goyaapp.dto.response.AccountResponse
import com.group.goyaapp.dto.response.DefaultRes
import com.group.goyaapp.dto.response.ResponseMessage
import com.group.goyaapp.dto.response.StatusCode
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
	): DefaultRes<out Any> {
		try {
			val result = accountService.saveAccount(request)
			return DefaultRes.res(StatusCode.CREATED, ResponseMessage.SIGNUP_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@GetMapping("/account")
	fun getAccountList(): DefaultRes<List<AccountResponse>> {
		val result = accountService.getAccountAll()
		return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_ACCOUNT_SUCCESS, result)
	}
	
	@PostMapping("/account/withdrawal")
	fun deleteAccount(
		@RequestBody
		request: AccountDeleteRequest
	): DefaultRes<out String> {
		try {
			accountService.deleteAccount(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.WITHDRAWAL_SUCCESS, "")
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@PostMapping("/account/login")
	fun login(
		@RequestBody
		request: AccountUpdateRequest
	): DefaultRes<out Any> {
		try {
			val result = accountService.updateAccountLogin(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@PostMapping("/account/logout")
	fun logout(
		@RequestBody
		request: AccountLogoutRequest
	): DefaultRes<out Any> {
		try {
			val result = accountService.updateAccountLogout(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGOUT_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
}