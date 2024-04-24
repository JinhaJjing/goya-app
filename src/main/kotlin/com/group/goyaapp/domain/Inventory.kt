package com.group.goyaapp.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Inventory(

    @Column(name = "user_uid", unique = true)
    var userUid: Long,

    @Column(name = "pw")
    var accountPW: String,

    @Id
    @Column(name = "rid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val rid: Long? = null,

    //@OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    //val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(),
) {
    @Column(name = "datetime_add")
    var datetimeAdd: LocalDateTime? = null

    @Column(name = "datetime_mod")
    var datetimeMod: LocalDateTime? = null

    @Column(name = "datetime_last_login")
    var datetimeLastLogin: LocalDateTime? = null

    init {
    }
}