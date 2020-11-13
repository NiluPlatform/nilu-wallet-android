package tech.nilu.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.nilu.domain.model.NetworkObject
import tech.nilu.domain.model.wallet.WalletObject

interface WalletRepository {

    suspend fun getWallet(id: Long): WalletObject

    suspend fun getNetwork(id: Long): NetworkObject

    fun getActiveNetwork(): Flow<NetworkObject>
}
