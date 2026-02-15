package com.example.data.mapper

import com.example.data.modules.UserDto
import com.example.domain.dataClasses.User


fun UserDto.toUser(): User {
    return User(
        email =email,
        username = username,
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        email = email,
        username = username,
    )
}