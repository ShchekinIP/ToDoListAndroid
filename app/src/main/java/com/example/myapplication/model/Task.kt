package com.example.myapplication.model

import java.util.*
import java.io.Serializable

class Task(
    var id: Int?,
    val name: String,
    val date: Date,
    val desc: String,
    var done: Boolean
) : Serializable {
    constructor() : this(null, "", Date(), "", false)
}
