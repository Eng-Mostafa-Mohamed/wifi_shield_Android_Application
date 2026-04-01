package com.example.data.modules

data class RouterLinkRequest(
    val user_id: String,
    val email: String,
    val email_password: String,
    val router_username: String,
    val router_password: String
)