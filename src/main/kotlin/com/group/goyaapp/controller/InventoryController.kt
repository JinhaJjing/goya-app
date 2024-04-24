package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.InventoryUpdateRequest
import com.group.goyaapp.service.InventoryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InventoryController(
    private val inventoryService: InventoryService,
) {

    @PostMapping("/inventory")
    fun saveUser(@RequestBody request: InventoryUpdateRequest) {
        inventoryService.saveAccount(request)
    }

    // TODO 아이템 획득
    // TODO 아이템 사용/폐기
}