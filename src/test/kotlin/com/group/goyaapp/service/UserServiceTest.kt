package com.group.goyaapp.service

import com.group.goyaapp.domain.User
import com.group.goyaapp.dto.request.user.UserRequest
import com.group.goyaapp.dto.request.user.UserUpdateRequest
import com.group.goyaapp.repository.UserRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
	private val userRepository: UserRepository,
	private val userService: UserService,
) {
	
	@AfterEach
	fun clean() {
		userRepository.deleteAll()
	}
	
	@Test
	@DisplayName("유저 저장이 정상 동작한다")
	fun saveUserTest() { // given
		val request = UserUpdateRequest(1, "진하찡")
		
		// when
		userService.updateUser(request)
		
		// then
		val results = userRepository.findAll()
		assertThat(results).hasSize(1)
		assertThat(results[0].nickname).isEqualTo("최태현")
		//assertThat(results[0].level).isNull()
	}
	
	@Test
	@DisplayName("유저 조회가 정상 동작한다")
	fun getUsersTest() { // given
		userRepository.saveAll(
			listOf(
				User(1, "JinhaJjing"),
				User(1, "Yalru"),
			)
		)
		
		// when
		val results = userService.getUserAll()
		
		// then
		assertThat(results).hasSize(2) // [UserResponse(), UserResponse()]
		assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B") // ["A", "B"]
		assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null)
	}
	
	@Test
	@DisplayName("유저 삭제가 정상 동작한다")
	fun deleteUserTest() { // given
		userRepository.save(User(1, "JinhaJjing"))
		
		// when
		userService.deleteUser(UserRequest(1))
		
		// then
		assertThat(userRepository.findAll()).isEmpty()
	}
	
	@Test
	@DisplayName("Create user with unique nickname")
	fun shouldCreateUserWithUniqueNickname() {        // given
		val request = UserUpdateRequest(1, "UniqueNick")
		
		// when
		userService.updateUser(request)
		
		// then
		val savedUser = userRepository.findByNickname("UniqueNick")
		assertThat(savedUser).isNotNull()
	}
	
	@Test
	@DisplayName("Fail to create user with duplicate nickname")
	fun shouldFailToCreateUserWithDuplicateNickname() {        // given
		userRepository.save(User(1, "DuplicateNick"))
		val request = UserUpdateRequest(1, "DuplicateNick")
		
		// when
		val exception = assertThrows<Exception> {
			userService.updateUser(request)
		}
		
		// then
		assertThat(exception.message).isEqualTo("닉네임이 중복됩니다.")
	}
	
	@Test
	@DisplayName("Update user with unique nickname")
	fun shouldUpdateUserWithUniqueNickname() {        // given
		val savedUser = userRepository.save(User(1, "OldNick"))
		val request = UserUpdateRequest(savedUser.userUid, "NewNick")
		
		// when
		userService.updateUser(request)
		
		// then
		val updatedUser = userRepository.findByUserUid(savedUser.userUid)
		if (updatedUser != null) {
			assertThat(updatedUser.nickname).isEqualTo("NewNick")
		}
	}
	
	@Test
	@DisplayName("Fail to update user with duplicate nickname")
	fun shouldFailToUpdateUserWithDuplicateNickname() {        // given
		userRepository.save(User(1, "ExistingNick"))
		val savedUser = userRepository.save(User(1, "OldNick"))
		val request = UserUpdateRequest(savedUser.rid!!, "ExistingNick")
		
		// when
		val exception = assertThrows<Exception> {
			userService.updateUser(request)
		}
		
		// then
		assertThat(exception.message).isEqualTo("닉네임이 중복됩니다.")
	}
	
	@Test
	@DisplayName("Delete existing user")
	fun shouldDeleteExistingUser() {        // given
		val savedUser = userRepository.save(User(1, "ToBeDeleted"))
		
		// when
		userService.deleteUser(UserRequest(savedUser.rid!!))
		
		// then
		assertThat(userRepository.findById(savedUser.rid!!)).isNull()
	}
	
	@Test
	@DisplayName("Fail to delete non-existing user")
	fun shouldFailToDeleteNonExistingUser() {        // when
		val exception = assertThrows<Exception> {
			userService.deleteUser(UserRequest(1))
		}
		
		// then
		assertThat(exception.message).isEqualTo("유저를 찾을 수 없습니다.")
	}
}