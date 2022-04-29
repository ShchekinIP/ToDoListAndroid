package com.example.myapplication.api

import java.io.Serializable

class AuthReq(val login: String, val password: String) : Serializable {
}