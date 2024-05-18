package com.group.goyaapp.domain

import javax.persistence.*

@Entity
class User constructor(
	
	@Column(name = "nickname")
	var nickname: String,
	
	@Column(name = "level")
	var level: Int = 0, // deprecated
	
	@Column(name = "exp")
	var exp: Int = 0, // deprecated
	
	@Column(name = "saved_map")
	var savedMap: String = "Ma_0001",
	
	@OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
	val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Int? = null,
) {
	init {
		if (nickname.isBlank()) {
			throw IllegalArgumentException("닉네임은 비어 있을 수 없습니다")
		}
		this.level = 0
		this.exp = 0
		this.savedMap = "Ma_0001"
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