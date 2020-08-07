package com.tamastudy.community

import com.tamastudy.community.entity.User
import com.tamastudy.community.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class CommunityApplication(@Autowired
                           private val userRepository: UserRepository) {

    @PostConstruct
    fun initUsers() {
        val user: User = User(1, "ricky", "javatechie@gmail.com", "1234")
        userRepository.save(user)
    }
}

fun main(
        args: Array<String>
) {
    runApplication<CommunityApplication>(*args)
}
