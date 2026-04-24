package com.example.funfacts.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.funfacts.data.database.AppDatabase
import com.example.funfacts.data.entities.CustomFactEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomFactDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: CustomFactDao

    @Before
    fun setup() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetAllFacts() = runBlocking {
        val fact = CustomFactEntity(text = "Testing is fun!")
        dao.insertFact(fact)

        val allFacts = dao.getAllFacts().first()

        assertThat(allFacts).hasSize(1)
        assertThat(allFacts[0].text).isEqualTo("Testing is fun!")
    }

    @Test
    fun deleteFact() = runBlocking {
        val fact = CustomFactEntity(id = 1, text = "Delete me")
        dao.insertFact(fact)
        dao.deleteFact(fact)

        val allFacts = dao.getAllFacts().first()

        assertThat(allFacts).isEmpty()
    }
}
