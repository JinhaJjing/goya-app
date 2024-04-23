package com.group.goyaapp.repository

import com.group.goyaapp.domain.QUserLoanHistory.userLoanHistory
import com.group.goyaapp.domain.UserLoanHistory
import com.group.goyaapp.domain.UserLoanStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserLoanHistoryQuerydslRepository(
  private val queryFactory: JPAQueryFactory,
) {

  fun find(bookName: String, status: UserLoanStatus? = null): UserLoanHistory? {
    return queryFactory.select(userLoanHistory)
      .from(userLoanHistory)
      .where(
        userLoanHistory.bookName.eq(bookName),
        status?.let { userLoanHistory.status.eq(status) }
      )
      .limit(1)
      .fetchOne()
  }

  fun count(status: UserLoanStatus): Long {
    return queryFactory.select(userLoanHistory.count())
      .from(userLoanHistory)
      .where(
        userLoanHistory.status.eq(status)
      )
      .fetchOne() ?: 0L
  }

}