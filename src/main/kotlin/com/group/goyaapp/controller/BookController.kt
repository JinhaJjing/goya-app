package com.group.goyaapp.controller

import com.group.goyaapp.dto.request.BookLoanRequest
import com.group.goyaapp.dto.request.BookRequest
import com.group.goyaapp.dto.request.BookReturnRequest
import com.group.goyaapp.dto.response.BookStatResponse
import com.group.goyaapp.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
  private val bookService: BookService,
) {

  @PostMapping("/book")
  fun saveBook(@RequestBody request: BookRequest) {
    bookService.saveBook(request)
  }

  @PostMapping("/book/loan")
  fun loanBook(@RequestBody request: BookLoanRequest) {
    bookService.loanBook(request)
  }

  @PutMapping("/book/return")
  fun returnBook(@RequestBody request: BookReturnRequest) {
    bookService.returnBook(request)
  }

  @GetMapping("/book/loan")
  fun countLoanedBook(): Int {
    return bookService.countLoanedBook()
  }

  @GetMapping("/book/stat")
  fun getBookStatistics(): List<BookStatResponse> {
    return bookService.getBookStatistics()
  }

}