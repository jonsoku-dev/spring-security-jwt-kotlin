package com.tamastudy.community.service

import com.tamastudy.community.entity.User
import com.tamastudy.community.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(@Autowired
                               var userRepository: UserRepository) : UserDetailsService {

    @ExperimentalStdlibApi
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username)
        return org.springframework.security.core.userdetails.User(user.username, user.password, ArrayDeque())
    }
}