package com.example.data.modules

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDto(
    @PrimaryKey
    val email: String,
    val username: String,
)