package com.group.goyaapp.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun failInputArgument(): Nothing {
  throw IllegalArgumentException()
}

fun failFindData(): Nothing {
  throw NoSuchElementException()
}

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
  return this.findByIdOrNull(id) ?: failInputArgument()
}