package tech.nilu.explorer

import kotlinx.coroutines.flow.Flow
import org.kodein.di.instance
import tech.nilu.explorer.database.DatabaseDriverFactory
import tech.nilu.explorer.database.Network
import tech.nilu.explorer.di.kodein
import tech.nilu.explorer.network.NetworkRepository

abstract class NetworkSdk(driver: DatabaseDriverFactory) {

    protected val repository: NetworkRepository by kodein.di.instance(arg = driver)

    suspend fun addNetworks(networks: List<Network>) = repository.addNetworks(networks)

    abstract fun count(): Flow<Long>
}
