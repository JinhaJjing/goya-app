package com.group.goyaapp.service

import com.group.goyaapp.dto.request.InventoryUpdateRequest
import com.group.goyaapp.dto.response.InventoryResponse
import com.group.goyaapp.repository.InventoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class InventoryService (
    private val inventoryRepository: InventoryRepository,
) {
    @Transactional(readOnly = true)
    fun getInventoryAll(): List<InventoryResponse> {
        return inventoryRepository.findAll()
            .map { account -> InventoryResponse.of(account) }
    }

    @Transactional
    fun updateInventory(request: InventoryUpdateRequest): InventoryResponse {
        val inventory = inventoryRepository.findByUserUid(request.userUid)
        inventory.itemCount +=1
        return inventoryRepository.findByUserUid(request.userUid).let { InventoryResponse.of(it!!) }
    }
}