package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.UserCreateRequest
import com.group.goyaapp.dto.request.UserUpdateRequest
import com.group.goyaapp.dto.response.UserLoanHistoryResponse
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
  private val userService: UserService,
) {

  @PostMapping("/user")
  fun saveUser(@RequestBody request: UserCreateRequest) {
    userService.saveUser(request)
  }

  @GetMapping("/user")
  fun getUsers(): List<UserResponse> {
    return userService.getUsers()
  }

  @PutMapping("/user")
  fun updateUserName(@RequestBody request: UserUpdateRequest) {
    userService.updateUserName(request)
  }

  @DeleteMapping("/user")
  fun deleteUser(@RequestParam name: String) {
    userService.deleteUser(name)
  }

  @GetMapping("/user/loan")
  fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
    return userService.getUserLoanHistories()
  }

}