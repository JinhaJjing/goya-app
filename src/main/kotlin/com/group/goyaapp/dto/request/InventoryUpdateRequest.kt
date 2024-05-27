package com.group.goyaapp.dto.request

import jakarta.persistence.*

data class InventoryUpdateRequest (
    val userUid: Long,
    val type: EnumType // TODO : DELETE, INSERT???
)