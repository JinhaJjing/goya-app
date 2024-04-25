package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.InventoryUpdateRequest
import com.group.goyaapp.dto.response.InventoryResponse
import com.group.goyaapp.dto.response.UserResponse
import com.group.goyaapp.service.InventoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InventoryController(
    private val inventoryService: InventoryService,
) {

    @PostMapping("/inventory")
    fun saveUser(@RequestBody request: InventoryUpdateRequest) {
        //inventoryService.saveAccount(request)
    }

    // 모든 유저 조회(치트용)
    @GetMapping("/inventory")
    fun getInventory(): List<InventoryResponse> {
        return inventoryService.getInventoryAll()
    }


    // TODO 아이템 획득
    // TODO 아이템 사용/폐기
}