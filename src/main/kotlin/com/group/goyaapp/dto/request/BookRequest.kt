package com.group.goyaapp.dto.request

import com.group.goyaapp.domain.BookType

data class BookRequest(
    val name: String,
    val type: BookType,
)
