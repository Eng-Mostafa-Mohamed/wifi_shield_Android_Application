package com.example.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.modules.UserDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Database(arrayOf(UserDto::class),version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract fun getUserDao(): UserDao


    companion object{
        private var databaseInstance: UserDatabase?=null

        fun getInstance(applicationContext: Context): UserDatabase{
            if (databaseInstance==null){
                databaseInstance= Room.databaseBuilder(
                    applicationContext,
                    UserDatabase::class.java,
                    "users"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return databaseInstance!!
        }}

}


