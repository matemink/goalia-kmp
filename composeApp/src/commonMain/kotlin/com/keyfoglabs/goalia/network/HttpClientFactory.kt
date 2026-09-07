package com.keyfoglabs.goalia.network

import io.ktor.client.*

expect class HttpClientFactory() {
    fun create(): HttpClient
}
