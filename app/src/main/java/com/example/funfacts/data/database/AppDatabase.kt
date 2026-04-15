package com.example.funfacts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.funfacts.data.dao.CustomFactDao
import com.example.funfacts.data.entities.CustomFactEntity

@Database(
    entities = [CustomFactEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): CustomFactDao
}