package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.Inventory

data class InventoryResponse(
    val user_uid: Long,
    //val count: Long, // TODO 아이템 리스트
) {
    companion object {
        fun of(account: Inventory): InventoryResponse {
            return InventoryResponse(
                user_uid = account.userUid,
            )
        }
    }

}