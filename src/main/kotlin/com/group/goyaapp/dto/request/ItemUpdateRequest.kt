package com.group.goyaapp.dto.request

data class ItemUpdateRequest (
    val userUid: Long,
    val itemId: String,
    val itemCount: Long
)