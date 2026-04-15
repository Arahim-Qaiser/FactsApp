package com.example.funfacts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_facts")
data class CustomFactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String
)