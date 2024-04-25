package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.User

data class UserLoanHistoryResponse(
    val name: String, // 유저 이름
    val books: List<BookHistoryResponse>
) {
    companion object {
        fun of(user: User): UserLoanHistoryResponse {
            return UserLoanHistoryResponse(
                name = user.nickname,
                books = user.userLoanHistories.map(BookHistoryResponse.Companion::of)
            )
        }
    }
}

