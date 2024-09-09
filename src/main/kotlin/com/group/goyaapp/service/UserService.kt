package com.group.goyaapp.service

import com.group.goyaapp.domain.User
import com.group.goyaapp.dto.request.user.UserRequest
import com.group.goyaapp.dto.request.user.UserUpdateRequest
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.repository.AccountRepository
import com.group.goyaapp.repository.QuestRepository
import com.group.goyaapp.repository.QuestRepository2
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.util.getServerDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
	private val userRepository: UserRepository,
	private val accountRepository: AccountRepository,
	private val questRepository: QuestRepository,
	private val questRepository2: QuestRepository2,
) {
	@Transactional
	fun updateUser(request: UserUpdateRequest): UserResponse {
		accountRepository.findByUserUid(request.userUid) ?: throw Exception("해당 유저가 존재하지 않습니다.")
		var curUser = userRepository.findByUserUid(request.userUid)
		if (curUser == null || curUser.nickname == "") curUser = User(request.userUid)
		else curUser.datetimeAdd = getServerDateTime()
		
		val reqNickname = request.nickname.trim()
		if (reqNickname.isBlank()) throw Exception("닉네임을 입력해주세요.")
		if (reqNickname.length < 2 || reqNickname.length > 8) throw Exception("닉네임은 2자 이상 8자 이하로 입력해주세요.")
		if (!reqNickname.matches(Regex("^[a-zA-Z0-9ㄱ-ㅣ가-힣]*$"))) throw Exception("닉네임은 한글, 영어, 숫자만 입력해주세요.")
		
		curUser.updateNickName(reqNickname)
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
		questRepository2.findByUserUid(request.userUid)?.forEach(questRepository2::delete)
		// 게임 초기화할 컨텐츠가 더 생기면 추가 필요
	}
}