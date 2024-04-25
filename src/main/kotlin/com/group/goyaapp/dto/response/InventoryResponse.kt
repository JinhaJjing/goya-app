package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.Inventory

data class InventoryResponse(
    val itemId: String,
    val itemCount: Long
) {
    companion object {
        fun of(inventory: Inventory): InventoryResponse {
            return InventoryResponse(
                itemId = inventory.itemId,
                itemCount = inventory.itemCount
            )
        }
    }

}