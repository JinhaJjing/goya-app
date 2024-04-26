package com.group.goyaapp.repository

import com.group.goyaapp.domain.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {
	
	fun findByName(bookName: String): Book?
	
}