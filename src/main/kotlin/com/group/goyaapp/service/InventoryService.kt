package com.group.goyaapp.service

import com.group.goyaapp.domain.Inventory
import com.group.goyaapp.dto.request.ItemUpdateRequest
import com.group.goyaapp.dto.response.InventoryResponse
import com.group.goyaapp.repository.InventoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class InventoryService(
	private val inventoryRepository: InventoryRepository,
) {
	@Transactional(readOnly = true)
	fun getInventoryAll(): List<InventoryResponse> {
		return inventoryRepository.findAll().map { account -> InventoryResponse.of(account) }
	}
	
	@Transactional
	fun updateInventory(request: ItemUpdateRequest): InventoryResponse {
		val inventory = inventoryRepository.findByUserUidAndItemId(request.userUid, request.itemId)
		if (inventory != null) {
			inventory.itemCount += request.itemCount
			inventory.datetimeMod = LocalDateTime.now()
		}
		else {
			inventoryRepository.save(Inventory(request.userUid, request.itemId, request.itemCount))
		}
		return inventoryRepository.findByUserUid(request.userUid).let { InventoryResponse.of(it!!) }
	}
	
	// TODO 아이템 획득
	// TODO 아이템 사용/폐기
}