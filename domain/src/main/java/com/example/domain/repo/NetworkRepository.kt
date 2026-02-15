package com.example.domain.repo

import com.example.domain.dataClasses.PingResult

interface  NetworkRepository{
    suspend fun pingTest(): PingResult
}