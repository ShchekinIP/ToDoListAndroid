package com.example.myapplication

import com.example.myapplication.model.Task
import org.junit.Test
import org.junit.jupiter.api.Assertions
import java.util.*

class TaskTest {
    @Test
    fun createTaskTest() {

        val task1 = Task()
        Assertions.assertFalse(task1.done)

        val task2 = Task(
            1,
            "name",
            Date(),
            "description",
            true
        )
        Assertions.assertTrue(task2.done)


    }
}