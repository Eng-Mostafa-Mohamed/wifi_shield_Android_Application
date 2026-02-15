package com.example.data.core

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.modules.UserDto

@Dao
interface UserDao {

    @Upsert
    suspend fun addUser(user: UserDto)

    @Query("Select username from users WHERE email= :log_email ")
    suspend fun getUserName(log_email:String): String?


}