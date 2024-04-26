package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.user.UserCreateRequest
import com.group.goyaapp.dto.request.user.UserRequest
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
	): UserResponse {
		return userService.saveUser(request)
	}
	
	@GetMapping("/userAll")
	fun getUsers(): List<UserResponse> {
		return userService.getUserAll()
	}
	
	@PostMapping("/user/info")
	fun getUsers(
		@RequestBody
		request: UserRequest
	): UserResponse {
		return userService.getUser(request)
	}
	
	@DeleteMapping("/user/delete")
	fun deleteUser(
		@RequestParam
		userUid: Int
	) {
		userService.deleteUser(userUid)
	}/*
      @GetMapping("/user/loan")
      fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userService.getUserLoanHistories()
      }
    */
}