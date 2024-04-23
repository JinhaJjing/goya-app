package com.group.goyaapp.service

import com.group.goyaapp.domain.Book
import com.group.goyaapp.repository.BookRepository
import com.group.goyaapp.repository.UserRepository
import com.group.goyaapp.domain.UserLoanStatus
import com.group.goyaapp.dto.request.BookLoanRequest
import com.group.goyaapp.dto.request.BookRequest
import com.group.goyaapp.dto.request.BookReturnRequest
import com.group.goyaapp.dto.response.BookStatResponse
import com.group.goyaapp.repository.BookQuerydslRepository
import com.group.goyaapp.repository.UserLoanHistoryQuerydslRepository
import com.group.goyaapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
  private val bookRepository: BookRepository,
  private val bookQuerydslRepository: BookQuerydslRepository,
  private val userRepository: UserRepository,
  private val userLoanHistoryQuerydslRepository: UserLoanHistoryQuerydslRepository,
) {

  @Transactional
  fun saveBook(request: BookRequest) {
    val book = Book(request.name, request.type)
    bookRepository.save(book)
  }

  @Transactional
  fun loanBook(request: BookLoanRequest) {
    val book = bookRepository.findByName(request.bookName) ?: fail()
    if (userLoanHistoryQuerydslRepository.find(request.bookName, UserLoanStatus.LOANED) != null) {
      throw IllegalArgumentException("진작 대출되어 있는 책입니다")
    }

    val user = userRepository.findByName(request.userName) ?: fail()
    user.loanBook(book)
  }

  @Transactional
  fun returnBook(request: BookReturnRequest) {
    val user = userRepository.findByName(request.userName) ?: fail()
    user.returnBook(request.bookName)
  }

  @Transactional(readOnly = true)
  fun countLoanedBook(): Int {
    return userLoanHistoryQuerydslRepository.count(UserLoanStatus.LOANED).toInt()
  }

  @Transactional(readOnly = true)
  fun getBookStatistics(): List<BookStatResponse> {
    return bookQuerydslRepository.getStats()
  }

}