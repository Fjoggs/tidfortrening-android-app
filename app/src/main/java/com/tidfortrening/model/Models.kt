package com.tidfortrening.model

import java.util.*

data class Activity(
    val startDate: Date,
    val endDate: Date,
    val exercise: Exercise,
    val users: List<User>
)

data class Exercise(
    val name: String,
    val description: String
)

data class User(
    val name: String,
    val gender: String,
    val height: Int,
    val weight: Double,
    val age: Int,
    val maxHeartRate: Int
)