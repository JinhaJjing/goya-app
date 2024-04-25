package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.UserCreateRequest
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/user")
    fun saveUser(@RequestBody request: UserCreateRequest) {
        userService.saveUser(request)
    }

    // 모든 유저 조회(치트용)
    @GetMapping("/user")
    fun getUsers(): List<UserResponse> {
        return userService.getUserAll()
    }

    @DeleteMapping("/user")
    fun deleteUser(@RequestParam userUid: Int) {
        userService.deleteUser(userUid)
    }
    /*
      @GetMapping("/user/loan")
      fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userService.getUserLoanHistories()
      }
    */
}