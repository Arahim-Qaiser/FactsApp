package com.example.funfacts.data

import com.example.funfacts.data.dao.CustomFactDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactRepository @Inject constructor(
    private val factService: FactService,
    mockDao: CustomFactDao
) {
    suspend fun getRandomFacts(lang : String = "en") : Fact {
        return factService.getRandomFacts(lang)
    }
}
