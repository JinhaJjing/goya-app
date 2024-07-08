package com.group.goyaapp.service

import com.group.goyaapp.domain.User
import com.group.goyaapp.dto.request.user.UserRequest
import com.group.goyaapp.dto.request.user.UserUpdateRequest
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.repository.AccountRepository
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.util.getServerDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
	private val userRepository: UserRepository,
	private val accountRepository: AccountRepository,
	private val questRepository: QuestRepository,
) {
	@Transactional
	fun updateUser(request: UserUpdateRequest): UserResponse {
		accountRepository.findByUserUid(request.userUid) ?: throw Exception("계정이 존재하지 않습니다.")
		var curUser = userRepository.findByUserUid(request.userUid)
		if (curUser == null || curUser.nickname == "") curUser = User(request.userUid)
		else curUser.datetimeAdd = getServerDateTime()
		curUser.updateNickName(request.nickname)
		curUser.datetimeMod = getServerDateTime()
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
	fun deleteUser(request: UserRequest) {
		val user = userRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		userRepository.delete(user)
	}
	
	@Transactional
	fun initUser(request: UserRequest) {
		val curUser = userRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		userRepository.delete(curUser)
		questRepository.findByUserUid(request.userUid)?.forEach(questRepository::delete)
		// 게임 초기화할 컨텐츠가 더 생기면 추가 필요
	}
}