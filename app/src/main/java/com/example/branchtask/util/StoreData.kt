package com.example.branchtask.util

interface StoreData  {
    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String): String
}