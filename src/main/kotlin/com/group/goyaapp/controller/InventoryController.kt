package com.group.goyaapp.controller

import com.group.goyaapp.service.InventoryService
import org.springframework.web.bind.annotation.RestController

@RestController
class InventoryController(
	private val inventoryService: InventoryService,
) {
/*	// 모든 유저 조회(치트용)
	@GetMapping("/inventory")
	fun getInventory(): List<InventoryResponse> {
		return inventoryService.getInventoryAll()
	}*/
}