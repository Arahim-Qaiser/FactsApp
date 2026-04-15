package com.example.funfacts.data

import android.content.Context
import androidx.room.Room
import com.example.funfacts.data.database.AppDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "facts_db"
            ).build()

            INSTANCE = instance
            instance
        }
    }
}