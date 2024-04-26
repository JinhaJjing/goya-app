package com.group.goyaapp.dto.response

import com.group.goyaapp.domain.BookType

data class BookStatResponse(
	val type: BookType,
	val count: Long,
)