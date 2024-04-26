package com.group.goyaapp.dto.request.book

import com.group.goyaapp.domain.BookType

data class BookRequest(
    val name: String,
    val type: BookType,
)
