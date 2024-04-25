package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.UserLoanHistory

data class ItemResponse(
    val name: String, // 책의 이름
    val isReturn: Boolean,
) {
    companion object {
        fun of(history: UserLoanHistory): BookHistoryResponse {
            return BookHistoryResponse(
                name = history.bookName,
                isReturn = history.isReturn
            )
        }
    }
}
