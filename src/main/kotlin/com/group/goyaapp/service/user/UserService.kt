package com.group.goyaapp.service.user

import com.group.goyaapp.domain.user.User
import com.group.goyaapp.domain.user.UserRepository
import com.group.goyaapp.dto.user.request.UserCreateRequest
import com.group.goyaapp.dto.user.request.UserUpdateRequest
import com.group.goyaapp.dto.user.response.UserLoanHistoryResponse
import com.group.goyaapp.dto.user.response.UserResponse
import com.group.goyaapp.util.fail
import com.group.goyaapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  private val userRepository: UserRepository,
) {

  @Transactional
  fun saveUser(request: UserCreateRequest) {
    val newUser = User(request.name, request.age)
    userRepository.save(newUser)
  }

  @Transactional(readOnly = true)
  fun getUsers(): List<UserResponse> {
    return userRepository.findAll()
      .map { user -> UserResponse.of(user) }
  }

  @Transactional
  fun updateUserName(request: UserUpdateRequest) {
    val user = userRepository.findByIdOrThrow(request.id)
    user.updateName(request.name)
  }

  @Transactional
  fun deleteUser(name: String) {
    val user = userRepository.findByName(name) ?: fail()
    userRepository.delete(user)
  }

  @Transactional(readOnly = true)
  fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
    return userRepository.findAllWithHistories()
      .map(UserLoanHistoryResponse::of)
  }

}