package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.Account

data class AccountResponse(
	val userUid: Long,
	val id: String,
	val pw: String,
) {
	
	companion object {
		fun of(account: Account): AccountResponse {
			return AccountResponse(
				userUid = account.userUid!!,
				id = account.accountId,
				pw = account.accountPW,
			)
		}
	}
	
}