package com.group.goyaapp.domain

import javax.persistence.*

@Entity
class User constructor(

    @Column(name = "nickname")
    var nickname: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    @Column(name = "level")
    val level: Int? = 0

    @Column(name = "exp")
    val exp: Int? = 0

    @Column(name = "savepoint")
    val savepoint: Int? = 0

    init {
        if (nickname.isBlank()) {
            throw IllegalArgumentException("닉네임은 비어 있을 수 없습니다")
        }
    }

    fun updateNickName(name: String) {
        this.nickname = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(this, book.name))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories.first { history -> history.bookName == bookName }.doReturn()
    }

}