package com.group.goyaapp.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Inventory(

    @Column(name = "user_uid")
    var userUid: Long,

    @Column(name = "item_id")
    var itemId: String,

    @Column(name = "item_count")
    var itemCount: Long,

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

    init {
        this.datetimeAdd = LocalDateTime.now()
        this.datetimeMod = LocalDateTime.now()
    }
}