package com.example.domain.dataClasses

data class AlertModel(
    val title: String,
    val message: String,
    val device: String,
    val confidence: String,
    val time: String
)