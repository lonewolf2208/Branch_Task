package com.example.branchtask.repo

import com.example.branchtask.model.GetHomeResponse
import com.example.branchtask.model.LoginBody
import com.example.branchtask.model.LoginResponse
import com.example.branchtask.network.Api
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody
import javax.inject.Inject

class HomeRepo @Inject constructor(private val api: Api): IHomeRepo{
    override suspend fun getHome(): ApiResponse<List<GetHomeResponse>> = api.getHome()
    override suspend fun postMessage(id: String, body: String): ApiResponse<GetHomeResponse> = api.postMessages(id=id,body=body)
    override suspend fun reset(): ApiResponse<ResponseBody> = api.reset()
}

interface IHomeRepo
{
    suspend fun getHome():ApiResponse<List<GetHomeResponse>>
    suspend fun postMessage(id:String,body:String):ApiResponse<GetHomeResponse>
    suspend fun reset():ApiResponse<ResponseBody>
}