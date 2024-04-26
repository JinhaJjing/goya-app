package com.group.goyaapp.service.user

import com.group.goyaapp.domain.User
import com.group.goyaapp.domain.UserLoanHistory
import com.group.goyaapp.domain.UserLoanStatus
import com.group.goyaapp.dto.request.user.UserCreateRequest
import com.group.goyaapp.repository.UserLoanHistoryRepository
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.service.UserService
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
  private val userRepository: UserRepository,
  private val userService: UserService,
  private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

  @AfterEach
  fun clean() {
    userRepository.deleteAll()
  }

  @Test
  @DisplayName("유저 저장이 정상 동작한다")
  fun saveUserTest() {
    // given
    val request = UserCreateRequest("진하찡")

    // when
    userService.saveUser(request)

    // then
    val results = userRepository.findAll()
    assertThat(results).hasSize(1)
    assertThat(results[0].nickname).isEqualTo("최태현")
    assertThat(results[0].level).isNull()
  }

  @Test
  @DisplayName("유저 조회가 정상 동작한다")
  fun getUsersTest() {
    // given
    userRepository.saveAll(listOf(
      User("JinhaJjing"),
      User("Yalru"),
    ))

    // when
    val results = userService.getUserAll()

    // then
    assertThat(results).hasSize(2) // [UserResponse(), UserResponse()]
    assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B") // ["A", "B"]
    assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null)
  }

  @Test
  @DisplayName("유저 삭제가 정상 동작한다")
  fun deleteUserTest() {
    // given
    userRepository.save(User("JinhaJjing"))

    // when
    userService.deleteUser(1)

    // then
    assertThat(userRepository.findAll()).isEmpty()
  }

  @Test
  @DisplayName("대출 기록이 없는 유저도 응답에 포함된다")
  fun getUserLoanHistoriesTest1() {
    // given
    userRepository.save(User("얄루진하"))

    // when
    val results = userService.getUserLoanHistories()

    // then
    assertThat(results).hasSize(1)
    assertThat(results[0].name).isEqualTo("얄루진하")
  }

  @Test
  @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다")
  fun getUserLoanHistoriesTest2() {
    // given
    val savedUser = userRepository.save(User("얄루얄루"))
    userLoanHistoryRepository.saveAll(listOf(
      UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
      UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
      UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED),
    ))

    // when
    val results = userService.getUserLoanHistories()

    // then
    assertThat(results).hasSize(1)
    assertThat(results[0].name).isEqualTo("A")
    assertThat(results[0].books).hasSize(3)
    assertThat(results[0].books).extracting("name")
      .containsExactlyInAnyOrder("책1", "책2", "책3")
    assertThat(results[0].books).extracting("isReturn")
      .containsExactlyInAnyOrder(false, false, true)
  }

}