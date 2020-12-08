package tech.nilu.explorer.network

import io.ktor.client.*
import io.ktor.client.engine.ios.*

internal actual fun getPlatformHttpClient(): HttpClient = HttpClient(Ios)
