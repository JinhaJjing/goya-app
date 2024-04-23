package com.group.goyaapp.repository

import com.group.goyaapp.domain.user.QUser.user
import com.group.goyaapp.domain.User
import com.group.goyaapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.querydsl.jpa.impl.JPAQueryFactory

class UserRepositoryCustomImpl(
  private val queryFactory: JPAQueryFactory,
) : UserRepositoryCustom {

  override fun findAllWithHistories(): List<User> {
    return queryFactory.select(user).distinct()
      .from(user)
      .leftJoin(userLoanHistory).on(userLoanHistory.user.id.eq(user.id)).fetchJoin()
      .fetch()
  }

}