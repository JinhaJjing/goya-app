package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.AccountCreateRequest
import com.group.goyaapp.dto.request.AccountUpdateRequest
import com.group.goyaapp.dto.response.AccountResponse
import com.group.goyaapp.service.AccountService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val accountService: AccountService,
) {

    @PostMapping("/account")
    fun saveUser(@RequestBody request: AccountCreateRequest) {
        accountService.saveAccount(request)
    }

    @GetMapping("/account")
    fun getUsers(): List<AccountResponse> {
        return accountService.getAccountAll()
    }

    @PutMapping("/account")
    fun updateUserName(@RequestBody request: AccountUpdateRequest) {
        accountService.updatePW(request)
    }

    @DeleteMapping("/account")
    fun deleteUser(@RequestParam id: String) {
        accountService.deleteAccount(id)
    }
}