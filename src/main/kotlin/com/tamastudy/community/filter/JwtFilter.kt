package com.tamastudy.community.filter

import com.tamastudy.community.service.CustomUserDetailsService
import com.tamastudy.community.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(@Autowired
                private val jwtUtil: JwtUtil,
                @Autowired
                private val service: CustomUserDetailsService) : OncePerRequestFilter() {

    @ExperimentalStdlibApi
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(httpServletRequest: HttpServletRequest,
                                  httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        val authorizationHeader = httpServletRequest.getHeader("Authorization")
        var token: String? = null
        var userName: String? = null
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7)
            userName = jwtUtil!!.extractUsername(token)
        }
        if (userName != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = service!!.loadUserByUsername(userName)
            if (jwtUtil!!.validateToken(token, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }
}