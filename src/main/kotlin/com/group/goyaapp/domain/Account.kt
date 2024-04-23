package com.group.goyaapp.domain

import java.time.LocalDateTime
import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Account(

    var id: String,

    var pw: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val user_uid: Long? = null,

    //@OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    //val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
) {
    var datetime_add: LocalDateTime? = null

    var datetime_mod: LocalDateTime? = null

    var datetime_last_login: LocalDateTime? = null

    init {
        if (id.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
        if (pw.isBlank()) {
            throw IllegalArgumentException("비밀번호는 비어 있을 수 없습니다")
        }
    }

    fun updatePW(id: String) {
        this.id = id
    }

    /*
    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(this, book.name))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories.first { history -> history.bookName == bookName }.doReturn()
    }
*/
}