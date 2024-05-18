package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.enumType.BookType

data class BookStatResponse(
	val type: BookType,
	val count: Long,
)