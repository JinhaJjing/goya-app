package com.group.goyaapp.repository

import com.group.goyaapp.domain.Inventory
import org.springframework.data.jpa.repository.JpaRepository

interface InventoryRepository  : JpaRepository<Inventory, String> {

    fun findByUserUid(userUid: Long): Inventory?
    fun findByUserUidAndItemId(userUid: Long, itemId: String): Inventory?

}