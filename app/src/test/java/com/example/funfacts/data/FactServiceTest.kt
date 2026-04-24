package com.example.funfacts.data

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FactServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var service: FactService

    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FactService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getRandomFacts returns expected fact`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "id": "1",
                    "text": "The heart of a shrimp is located in its head."
                }
            """.trimIndent())
        server.enqueue(mockResponse)

        val fact = service.getRandomFacts()

        assertThat(fact.id).isEqualTo("1")
        assertThat(fact.text).isEqualTo("The heart of a shrimp is located in its head.")
    }

    @Test
    fun `getRandomFacts returns error when server returns error`(): Unit = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(500)
        server.enqueue(mockResponse)
        try {
            service.getRandomFacts()
        } catch (e: Exception) {
            assertThat(e).isNotNull()
        }
    }
}
