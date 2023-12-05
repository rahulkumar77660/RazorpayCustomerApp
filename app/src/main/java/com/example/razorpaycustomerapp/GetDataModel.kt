package com.example.razorpaycustomerapp

data class GetDataModel(
    val count: Int,
    val entity: String,
    val items: List<ItemX>
)