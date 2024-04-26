package com.group.goyaapp.service

import com.group.goyaapp.domain.User
import com.group.goyaapp.dto.request.UserCreateRequest
import com.group.goyaapp.dto.request.UserRequest
import com.group.goyaapp.dto.response.UserLoanHistoryResponse
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
	private val userRepository: UserRepository,
) {
	@Transactional
	fun saveUser(request: UserCreateRequest): UserResponse {
		val newUser = User(request.nickname)
		return userRepository.save(newUser).let { UserResponse.of(it) }
	}
	
	@Transactional(readOnly = true)
	fun getUserAll(): List<UserResponse> {
		return userRepository.findAll().map { user -> UserResponse.of(user) }
	}
	
	@Transactional(readOnly = true)
	fun getUser(request: UserRequest): UserResponse {
		val user = userRepository.findById(request.user_uid)
		return user.let { UserResponse.of(it!!) }
	}
	
	@Transactional
	fun deleteUser(userUid: Int) {
		val user = userRepository.findById(userUid) ?: fail()
		userRepository.delete(user)
	}
	
	@Transactional(readOnly = true)
	fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
		return userRepository.findAllWithHistories().map(UserLoanHistoryResponse::of)
	}
	
}