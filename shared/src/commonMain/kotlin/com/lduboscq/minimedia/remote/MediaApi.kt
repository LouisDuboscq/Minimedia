package com.lduboscq.minimedia.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

interface MediaApi {
    suspend fun fetch(): MediaApiResponse
}

class HttpMediaApi(
    private val httpClient: HttpClient,
) : MediaApi {

    companion object {
        private const val URL = "https://extendsclass.com/api/json-storage/bin/edfefba"

        val client = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

        }

        val instance = HttpMediaApi(httpClient = client)
    }

    override suspend fun fetch(): MediaApiResponse {
        delay(500) // fake a bad network
        val httpResponse = httpClient.get(URL)
        // todo here I had a weird ktor/serialization issue : "No transformation found: ByteBufferChannel", I used another method to decode json
        return Json.decodeFromString(MediaApiResponse.serializer(), httpResponse.body())
    }
}
