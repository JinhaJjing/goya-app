package com.group.goyaapp.service

import com.group.goyaapp.domain.User
import com.group.goyaapp.dto.request.user.UserCreateRequest
import com.group.goyaapp.dto.request.user.UserRequest
import com.group.goyaapp.dto.request.user.UserUpdateRequest
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
	fun createUser(request: UserCreateRequest): UserResponse {
		val newUser = User(request.nickname)
		if (!checkNicknameUnique(request.nickname)) {
			throw Exception("닉네임이 중복됩니다.")
		}
		return userRepository.save(newUser).let { UserResponse.of(it) }
	}
	
	@Transactional
	fun updateUser(request: UserUpdateRequest): UserResponse {
		val curUser = userRepository.findById(request.userUid) ?: fail()
		if (!checkNicknameUnique(request.nickname)) {
			throw Exception("닉네임이 중복됩니다.")
		}
		curUser.nickname = request.nickname
		return userRepository.save(curUser).let { UserResponse.of(it) }
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
	
	// NOT USED
	@Transactional(readOnly = true)
	fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
		return userRepository.findAllWithHistories().map(UserLoanHistoryResponse::of)
	}
	
	fun checkNicknameUnique(nickname: String): Boolean {
		return userRepository.findByNickname(nickname) == null
	}
	
}