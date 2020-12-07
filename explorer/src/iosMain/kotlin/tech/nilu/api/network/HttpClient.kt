package tech.nilu.api.network

import io.ktor.client.*
import io.ktor.client.engine.ios.*

internal actual fun getPlatformHttpClient(): HttpClient = HttpClient(Ios)
