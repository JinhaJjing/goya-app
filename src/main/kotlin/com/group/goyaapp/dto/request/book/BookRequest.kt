package com.group.goyaapp.dto.request.book

import com.group.goyaapp.domain.enumType.BookType

data class BookRequest(
	val name: String,
	val type: BookType,
)
