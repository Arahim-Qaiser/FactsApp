package com.example.funfacts.data.repository
import com.example.funfacts.data.dao.CustomFactDao
import com.example.funfacts.data.entities.CustomFactEntity

class CustomFactRepository(
    private val dao: CustomFactDao
) {

    val allFacts = dao.getAllFacts()

    suspend fun addFact(text: String) {
        dao.insertFact(
            CustomFactEntity(text = text)
        )
    }
    suspend fun deleteFact(fact: CustomFactEntity) {
        dao.deleteFact(fact)
    }

    suspend fun deleteFactByText(text: String) {
        dao.deleteFactByText(text)
    }

}
