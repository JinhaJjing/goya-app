package com.group.goyaapp.repository

import com.group.goyaapp.domain.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, String> {

    //fun findById(id: String): Account?

}