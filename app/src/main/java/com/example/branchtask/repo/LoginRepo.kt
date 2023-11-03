package com.example.branchtask.repo

import com.example.branchtask.model.LoginBody
import com.example.branchtask.model.LoginResponse
import com.example.branchtask.network.Api
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class LoginRepo @Inject constructor(private val api: Api): ILoginRepo{
    override suspend fun getLogin(data:LoginBody): ApiResponse<LoginResponse> = api.getLogin(data)
}

interface ILoginRepo
{
    suspend fun getLogin(data:LoginBody):ApiResponse<LoginResponse>
}