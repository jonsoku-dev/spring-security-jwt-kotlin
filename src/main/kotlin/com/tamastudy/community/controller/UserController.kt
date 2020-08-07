package com.tamastudy.community.controller

import com.tamastudy.community.request.AuthRequest
import com.tamastudy.community.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(
        @Autowired val jwtUtil: JwtUtil,
        @Autowired val authenticationManager: AuthenticationManager
) {

    @GetMapping("/dontneedtoken")
    fun dontneedtoken(): String? {
        return "dontneedtoken"
    }

    @GetMapping("/needtoken")
    fun needtoken(): String? {
        return "needtoken"
    }


    @PostMapping("/authenticate")
    @Throws(Exception::class)
    fun generateToken(@RequestBody authRequest: AuthRequest): String? {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(authRequest.userName, authRequest.password)
            )
        } catch (ex: Exception) {
            throw Exception("invalid username/password")
        }
        return jwtUtil.generateToken(authRequest.userName)
    }
}