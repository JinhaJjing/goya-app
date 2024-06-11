package com.group.goyaapp.service

import com.group.goyaapp.domain.User
import com.group.goyaapp.dto.request.user.UserCreateRequest
import com.group.goyaapp.dto.request.user.UserRequest
import com.group.goyaapp.dto.request.user.UserUpdateRequest
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.repository.AccountRepository
import com.group.goyaapp.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
	private val userRepository: UserRepository,
	private val accountRepository: AccountRepository,
) {
	@Transactional
	fun createUser(request: UserCreateRequest): UserResponse {
		accountRepository.findByUserUid(request.userUid) ?: throw Exception("계정이 존재하지 않습니다.")
		val isUserExists = userRepository.findByUserUid(request.userUid)
		if (isUserExists !== null && isUserExists.nickname != "") throw Exception("해당 유저의 닉네임이 이미 존재합니다.")
		val newUser = User(request.userUid)
		newUser.updateNickName(request.nickname)
		return userRepository.save(newUser).let { UserResponse.of(it) }
	}
	
	@Transactional
	fun updateNickname(request: UserUpdateRequest): UserResponse {
		val curUser = userRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		curUser.updateNickName(request.nickname)
		return userRepository.save(curUser).let { UserResponse.of(it) }
	}
	
	@Transactional(readOnly = true)
	fun getUserAll(): List<UserResponse> {
		return userRepository.findAll().map { user -> UserResponse.of(user) }
	}
	
	@Transactional(readOnly = true)
	fun getUser(request: UserRequest): UserResponse {
		val user = userRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		return user.let { UserResponse.of(it) }
	}
	
	@Transactional
	fun deleteUser(userUid: Long) {
		val user = userRepository.findByUserUid(userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		userRepository.delete(user)
	}
}