package com.example.branchtask.util

sealed class Resource <T> (
    val data : T ? =  null,
    val error : String ?= null
){
    class Success<T>(data: T?=null) : Resource<T>(data=data)
    class Loading<T> : Resource<T>()
    class Error<T>(msg: String) : Resource<T>(error= msg)
}
