package com.example.funfacts.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactRepository @Inject constructor() {
    suspend fun getRandomFacts(lang : String = "en") :Fact {
        return RetrofitInstance.factService.getRandomFacts(lang)
    }
}
