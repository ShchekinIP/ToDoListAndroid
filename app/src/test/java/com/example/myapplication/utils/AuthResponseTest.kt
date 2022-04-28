package com.example.myapplication.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions

class AuthResponseTest {
    @Test
    fun testConstructor() {
        val authRequest = AuthResponse("Token")
        Assertions.assertEquals("Token", authRequest.token)
    }
}