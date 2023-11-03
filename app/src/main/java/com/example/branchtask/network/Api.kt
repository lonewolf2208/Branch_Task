package com.example.branchtask.network

import com.example.branchtask.model.LoginBody
import com.example.branchtask.model.LoginResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {
    @POST("api/login")
    suspend fun getLogin(@Body data : LoginBody) : ApiResponse<LoginResponse>

    @GET("api/messages")
    suspend fun getHome(@Header("“X-Branch-Auth-Token") token:String):ApiResponse
}