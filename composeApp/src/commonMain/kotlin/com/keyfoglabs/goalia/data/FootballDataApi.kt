package com.keyfoglabs.goalia.data

import com.keyfoglabs.goalia.network.HttpClientFactory
import io.ktor.client.call.*
import io.ktor.client.request.*

class FootballDataApi {
    private val client = HttpClientFactory().create()

    suspend fun getMatches(): List<MatchDto> {
        val response: MatchesResponse =
            client.get(MATCHES_ENDPOINT).body()

        return response.matches
    }

    private companion object {
        const val MATCHES_ENDPOINT =
            "https://goalia-backend-a24b0579f0d9.herokuapp.com/api/matches"
    }
}
