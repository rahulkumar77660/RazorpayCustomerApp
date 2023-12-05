package com.example.razorpaycustomerapp

data class FetchDataModel(
    val count: Int,
    val entity: String,
    val items: List<Item>
)