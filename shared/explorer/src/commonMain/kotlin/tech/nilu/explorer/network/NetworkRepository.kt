package tech.nilu.explorer.network

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tech.nilu.explorer.coroutines.dispatcher
import tech.nilu.explorer.database.DatabaseDriverFactory
import tech.nilu.explorer.database.Network
import tech.nilu.explorer.database.NiluDatabase

class NetworkRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = NiluDatabase(databaseDriverFactory.createDriver())
    private val databaseQueries = database.networkQueries

    fun getActiveNetwork(success: (Network) -> Long) {
        GlobalScope.launch(dispatcher) {
            success(getActiveNetwork())
        }
    }

    fun addNetworks(networks: List<Network>, success: (Unit) -> Unit) {
        GlobalScope.launch(dispatcher) {
            success(addNetworks(networks))
        }
    }

    fun count() = databaseQueries.count()

    private fun getActiveNetwork(): Network =
        databaseQueries.getActiveNetwork()
            .executeAsOne()

    private fun addNetworks(networks: List<Network>) = databaseQueries.transaction {
        networks.forEach {
            databaseQueries.insert(
                id = it.id,
                name = it.name,
                address = it.address,
                active = it.active,
                symbol = it.symbol,
                chainId = it.chainId,
                explorerAddress = it.explorerAddress
            )
        }
    }

}
