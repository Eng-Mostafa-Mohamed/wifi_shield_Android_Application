package com.example.data.core.modules

import android.content.Context
import android.content.SharedPreferences


object SessionManager {

    private lateinit var prefs: SharedPreferences
    private const val TOKEN_KEY = "auth_token"


    fun init(context: Context) {
        prefs = context.getSharedPreferences("app_session", Context.MODE_PRIVATE)
    }


    var authToken: String?
        get() = prefs.getString(TOKEN_KEY, null)
        set(value) = prefs.edit().putString(TOKEN_KEY, value).apply()


}

