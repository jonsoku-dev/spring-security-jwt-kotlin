package com.tamastudy.community.repository.user

import com.tamastudy.community.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User
}