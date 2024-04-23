package com.group.goyaapp.service

import com.group.goyaapp.domain.Account
import com.group.goyaapp.repository.AccountRepository
import com.group.goyaapp.dto.request.AccountCreateRequest
import com.group.goyaapp.dto.request.AccountUpdateRequest
import com.group.goyaapp.dto.response.AccountResponse
import com.group.goyaapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    @Transactional
    fun saveAccount(request: AccountCreateRequest) {
        val newUser = Account(request.id, request.pw)
        newUser.datetime_add = LocalDateTime.now()
        accountRepository.save(newUser)
    }

    @Transactional(readOnly = true)
    fun getAccountAll(): List<AccountResponse> {
        return accountRepository.findAll()
            .map { account -> AccountResponse.of(account) }
    }

    @Transactional
    fun updatePW(request: AccountUpdateRequest) {
        val account = accountRepository.findByIdOrThrow(request.id)
        account.updatePW(request.pw)
    }

    @Transactional
    fun deleteAccount(id: String) {
        val account = accountRepository.findByIdOrThrow(id)
        accountRepository.delete(account)
    }
/*
    @Transactional(readOnly = true)
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return accountRepository.findAllWithHistories()
            .map(UserLoanHistoryResponse::of)
    }
*/

}