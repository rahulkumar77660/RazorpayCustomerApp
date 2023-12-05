package com.example.razorpaycustomerapp

data class PostDataModel(
    val contact: String,
    val email: String,
    val fail_existing: String,
    val gstin: String,
    val name: String,
    val notes: Notes
)