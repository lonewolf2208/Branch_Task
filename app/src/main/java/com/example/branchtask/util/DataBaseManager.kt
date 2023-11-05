package com.example.branchtask.util

import android.content.SharedPreferences

class DataBaseManager(private val sharedPreferences: SharedPreferences) : StoreData {
    override fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}