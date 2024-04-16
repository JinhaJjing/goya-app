package com.group.goyaapp.dto.book.response

import com.group.goyaapp.domain.book.BookType

data class BookStatResponse(
  val type: BookType,
  val count: Long,
)