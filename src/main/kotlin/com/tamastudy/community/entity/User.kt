package com.tamastudy.community.entity

import javax.persistence.*

@Entity
@Table(name = "user_tbl")
data class User(
        @Id @GeneratedValue
        @Column(name = "user_id")
        var id: Long,
        var username: String,
        var email: String,
        var password: String
)