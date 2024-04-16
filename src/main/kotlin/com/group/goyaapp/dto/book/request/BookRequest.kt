package com.group.goyaapp.dto.book.request

import com.group.goyaapp.domain.book.BookType

data class BookRequest(
  val name: String,
  val type: BookType,
)
