package com.group.goyaapp.dto.request.book

data class BookLoanRequest(
  val userUid: Int,
  val bookName: String
)