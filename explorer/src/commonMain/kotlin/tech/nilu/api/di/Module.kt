package tech.nilu.api.di

import io.ktor.client.*
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.singleton
import tech.nilu.api.ExplorerSdk
import tech.nilu.api.explorer.*
import tech.nilu.api.network.getPlatformHttpClient

internal val kodein = DI {

    bind<HttpClient>() with singleton { getPlatformHttpClient() }

    bind<Json>() with singleton { Json { ignoreUnknownKeys = true } }

    bind<Explorer>() with factory { chainId: Long ->
        when (chainId) {
            1L -> EthplorerExplorer()
            512L -> NiluExplorer()
            1313114L -> Ether1Explorer()
            3125659152L -> PirlExplorer()
            else -> throw IllegalStateException("No explorer implemented for this network!")
        }
    }

    bind<ExplorerSdk>() with singleton { ExplorerSdk() }
}
