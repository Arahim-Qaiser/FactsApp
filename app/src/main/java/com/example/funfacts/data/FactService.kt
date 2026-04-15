package com.example.funfacts.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface FactService {
    @GET("random.json")
    suspend fun getRandomFacts(
        @Query("language") lang : String = "en"
    ): Fact
}

object RetrofitInstance {
    private const val BASE_URL = "https://uselessfacts.jsph.pl/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val factService: FactService = retrofit.create(FactService::class.java)
}