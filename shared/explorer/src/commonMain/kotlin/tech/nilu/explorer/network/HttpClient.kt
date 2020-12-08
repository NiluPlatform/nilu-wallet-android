@file:JvmMultifileClass

package tech.nilu.explorer.network

import io.ktor.client.*
import kotlin.jvm.JvmMultifileClass

internal expect fun getPlatformHttpClient(): HttpClient

internal const val TIMEOUT = 30L
