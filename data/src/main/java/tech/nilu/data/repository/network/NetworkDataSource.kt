package tech.nilu.data.repository.network

import tech.nilu.data.dao.NetworkDao
import tech.nilu.data.entity.Network
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val dao: NetworkDao
) {

    suspend fun getNetwork(id: Long) =
        dao.getNetwork(id)

    suspend fun getNetwork(address: String) =
        dao.getNetwork(address)

    suspend fun getActiveNetwork() =
        dao.getActiveNetwork()

    fun getActiveNetworkObservable() =
        dao.getActiveNetworkObservable()

    suspend fun getNetworks() =
        dao.getNetworks()

    fun getNetworksObservable() =
        dao.getNetworksObservable()

    suspend fun deactivateNetworks() =
        dao.deactivateNetworks()

    suspend fun update(network: Network) =
        dao.update(network)
}
