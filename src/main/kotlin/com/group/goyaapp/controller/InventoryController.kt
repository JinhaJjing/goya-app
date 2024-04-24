package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.AccountCreateRequest
import com.group.goyaapp.dto.request.AccountDeleteRequest
import com.group.goyaapp.dto.request.AccountUpdateRequest
import com.group.goyaapp.dto.response.AccountResponse
import com.group.goyaapp.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class InventoryController(
    private val accountService: AccountService,
) {

    @PostMapping("/inventory")
    fun saveUser(@RequestBody request: AccountCreateRequest) {
        accountService.saveAccount(request)
    }
}