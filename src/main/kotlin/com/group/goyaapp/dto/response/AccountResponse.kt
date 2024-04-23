package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.Account

data class AccountResponse(
    val id: String,
    val pw: String,
) {

    companion object {
        fun of(account: Account): AccountResponse {
            return AccountResponse(
                id = account.id,
                pw = account.pw,
            )
        }
    }

}