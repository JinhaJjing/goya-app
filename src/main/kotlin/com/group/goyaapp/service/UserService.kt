package com.group.goyaapp.service

import com.group.goyaapp.domain.User
import com.group.goyaapp.dto.request.user.UserCreateRequest
import com.group.goyaapp.dto.request.user.UserRequest
import com.group.goyaapp.dto.request.user.UserUpdateRequest
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.repository.UserRepository
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
		val curUser = userRepository.findById(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		if (!checkNicknameUnique(request.nickname)) {
			throw Exception("닉네임이 중복됩니다.")
		}
		curUser.updateNickName(request.nickname)
		return userRepository.save(curUser).let { UserResponse.of(it) }
	}
	
	@Transactional(readOnly = true)
	fun getUserAll(): List<UserResponse> {
		return userRepository.findAll().map { user -> UserResponse.of(user) }
	}
	
	@Transactional(readOnly = true)
	fun getUser(request: UserRequest): UserResponse {
		val user = userRepository.findById(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		return user.let { UserResponse.of(it) }
	}
	
	@Transactional
	fun deleteUser(userUid: Int) {
		val user = userRepository.findById(userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		userRepository.delete(user)
	}
	
	fun checkNicknameUnique(nickname: String): Boolean {
		return userRepository.findByNickname(nickname) == null
	}
	
}