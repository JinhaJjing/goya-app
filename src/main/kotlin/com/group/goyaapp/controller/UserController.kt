package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.user.UserRequest
import com.group.goyaapp.dto.request.user.UserUpdateRequest
import com.group.goyaapp.dto.response.DefaultRes
import com.group.goyaapp.dto.response.ResponseMessage
import com.group.goyaapp.dto.response.StatusCode
import com.group.goyaapp.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
	private val userService: UserService,
) {
	@Operation(summary = "유저 생성", description = "도깨비 이름을 지어줍니다.")
	@PostMapping("/user/create")
	fun saveUser(
		@RequestBody
		request: UserUpdateRequest
	): DefaultRes<out Any> {
		try {
			val result = userService.updateUser(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.CREATE_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "유저 전체 조회(치트용)", description = "모든 유저의 정보를 조회합니다.")
	@GetMapping("/userAll")
	fun getUsers(): DefaultRes<out Any> {
		try {
			val result = userService.getUserAll()
			return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "유저 정보 조회", description = "특정 유저의 정보를 조회합니다.")
	@PostMapping("/user/info")
	fun getUsers(
		@RequestBody
		request: UserRequest
	): DefaultRes<out Any> {
		try {
			val result = userService.getUser(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "유저 삭제", description = "특정 유저를 삭제합니다.")
	@DeleteMapping("/user/delete")
	fun deleteUser(
		@RequestBody
		request: UserRequest
	): DefaultRes<out Any> {
		try {
			val result = userService.deleteUser(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
	
	@Operation(summary = "유저 초기화", description = "특정 유저의 게임 정보를 초기화합니다.")
	@PostMapping("/user/init")
	fun initUser(
		@RequestBody
		request: UserRequest
	): DefaultRes<out Any> {
		try {
			val result = userService.initUser(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.INIT_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, e.message)
		}
	}
}