package com.group.goyaapp.dto.request

import javax.persistence.EnumType

data class InventoryUpdateRequest (
    val userUid: Long,
    val type: EnumType // TODO : DELETE, INSERT???
)