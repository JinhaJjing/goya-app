package com.group.goyaapp.repository

import com.group.goyaapp.domain.UserLoanHistory
import org.springframework.data.jpa.repository.JpaRepository

interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long>