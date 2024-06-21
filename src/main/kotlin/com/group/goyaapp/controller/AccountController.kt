package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.account.AccountCreateRequest
import com.group.goyaapp.dto.request.account.AccountDeleteRequest
import com.group.goyaapp.dto.request.account.AccountLogoutRequest
import com.group.goyaapp.dto.request.account.AccountUpdateRequest
import com.group.goyaapp.dto.response.DefaultRes
import com.group.goyaapp.dto.response.ResponseMessage
import com.group.goyaapp.dto.response.StatusCode
import com.group.goyaapp.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
	private val accountService: AccountService,
) {
	@Operation(summary = "회원가입", description = "새로운 계정을 만듭니다.")
	@PostMapping("/account/signup")
	fun saveAccount(
		@RequestBody
		request: AccountCreateRequest
	): DefaultRes<out Any> {
		try {
			val result = accountService.saveAccount(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.SIGNUP_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "계정 전체 조회(치트용)", description = "모든 계정을 조회합니다.")
	@GetMapping("/account")
	fun getAccountList(): DefaultRes<out Any> {
		val result = accountService.getAccountAll()
		return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_ACCOUNT_SUCCESS, result)
	}
	
	@Operation(summary = "회원 탈퇴", description = "특정 계정을 삭제합니다.")
	@PostMapping("/account/withdrawal")
	fun deleteAccount(
		@RequestBody
		request: AccountDeleteRequest
	): DefaultRes<out Any> {
		try {
			accountService.deleteAccount(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.WITHDRAWAL_SUCCESS, "")
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "로그인", description = "로그인합니다.")
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
	
	@Operation(summary = "로그아웃", description = "로그아웃합니다.")
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
	
	@Operation(summary = "게스트 회원가입", description = "게스트 계정을 만듭니다.")
	@PostMapping("/account/guest")
	fun saveGuestAccount(): DefaultRes<out Any> {
		try {
			val result = accountService.saveGuestAccount()
			return DefaultRes.res(StatusCode.OK, ResponseMessage.SIGNUP_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
}