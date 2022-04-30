package com.example.myapplication.api

import android.content.Context

object ApiClient {
    private var BASE_URL = "http://10.0.2.2:8081/api/"

    fun getAuthService(context: Context): AuthService {
        return RetrofitClient.getClient(BASE_URL, context).create(AuthService::class.java)
    }

    fun getAccessService(context: Context): AccessService {
        return RetrofitClient.getClient(BASE_URL, context).create(AccessService::class.java)
    }
}