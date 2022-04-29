package com.example.myapplication.interfaces

import com.example.myapplication.model.Task

interface TaskClickListener {
    fun onTaskClick(task: Task)
    fun onTaskCheckClick(id: Int)
}