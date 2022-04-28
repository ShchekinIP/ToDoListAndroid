package com.example.myapplication.utils

import org.junit.Test
import org.junit.jupiter.api.Assertions

class FormModelTest {
    @Test
    fun testConstructor() {
        val loginModel = FormModel()
        Assertions.assertNotNull(loginModel)
    }
}