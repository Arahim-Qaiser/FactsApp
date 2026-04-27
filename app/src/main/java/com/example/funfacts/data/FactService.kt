package com.example.funfacts.data

import retrofit2.http.GET
import retrofit2.http.Query

interface FactService {
    @GET("random.json")
    suspend fun getRandomFacts(
        @Query("language") lang : String = "en"
    ): Fact
}
