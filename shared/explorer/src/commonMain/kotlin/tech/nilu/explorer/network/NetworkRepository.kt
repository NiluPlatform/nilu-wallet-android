package tech.nilu.explorer.network

import tech.nilu.explorer.database.DatabaseDriverFactory
import tech.nilu.explorer.database.Network
import tech.nilu.explorer.database.NiluDatabase

class NetworkRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = NiluDatabase(databaseDriverFactory.createDriver())
    private val databaseQueries = database.networkQueries

    fun count() = databaseQueries.count()

    suspend fun getActiveNetwork(): Network =
        databaseQueries.getActiveNetwork()
            .executeAsOne()

    suspend fun addNetworks(networks: List<Network>) = databaseQueries.transaction {
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
