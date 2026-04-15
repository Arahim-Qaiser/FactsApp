package com.example.funfacts.data

class FactRepository {
    suspend fun getRandomFacts(lang : String = "en") :Fact {
   return RetrofitInstance.factService.getRandomFacts(lang)
    }
}