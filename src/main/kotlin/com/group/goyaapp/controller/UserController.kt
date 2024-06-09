package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.user.UserCreateRequest
import com.group.goyaapp.dto.request.user.UserRequest
import com.group.goyaapp.dto.request.user.UserUpdateRequest
import com.group.goyaapp.dto.response.DefaultRes
import com.group.goyaapp.dto.response.ResponseMessage
import com.group.goyaapp.dto.response.StatusCode
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
	private val userService: UserService,
) {
	
	@PostMapping("/user/create")
	fun saveUser(
		@RequestBody
		request: UserCreateRequest
	): DefaultRes<out Any> {
		try {
			val result = userService.createUser(request)
			return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATE_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.CREATE_USER_FAIL, e.message)
		}
	}
	
	@PostMapping("/user/updateNickname")
	fun updateUser(
		@RequestBody
		request: UserUpdateRequest
	): DefaultRes<out Any> {
		try {
			val result = userService.updateUser(request)
			return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATE_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.CREATE_USER_FAIL, e.message)
		}
	}
	
	@GetMapping("/userAll")
	fun getUsers(): DefaultRes<out Any> {
		try {
			val result = userService.getUserAll()
			return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, e.message)
		}
	}
	
	@PostMapping("/user/info")
	fun getUsers(
		@RequestBody
		request: UserRequest
	): DefaultRes<out Any> {
		try {
			val result = userService.getUser(request)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER, e.message)
		}
	}
	
	@DeleteMapping("/user/delete")
	fun deleteUser(
		@RequestParam
		userUid: Int
	): DefaultRes<out Any> {
		try {
			val result = userService.deleteUser(userUid)
			return DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_USER_SUCCESS, result)
		} catch (e: Exception) {
			return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.DELETE_USER_FAIL, e.message)
		}
	}
}