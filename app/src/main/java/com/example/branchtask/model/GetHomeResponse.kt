package com.example.branchtask.model

data class GetHomeResponse(
    val agent_id: String,
    val body: String,
    val id: String,
    val thread_id: String,
    val timestamp: String,
    val user_id: String
)