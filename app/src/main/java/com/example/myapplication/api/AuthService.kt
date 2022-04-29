package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    fun register(@Body request: AuthReq): Call<AuthRes>

    @POST("auth/sign_in")
    fun signIn(@Body request: AuthReq): Call<AuthRes>
}