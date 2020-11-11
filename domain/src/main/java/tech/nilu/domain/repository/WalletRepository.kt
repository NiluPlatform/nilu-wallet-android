package tech.nilu.domain.repository

import tech.nilu.domain.model.wallet.WalletObject

interface WalletRepository {

    suspend fun getWallet(id: Long): WalletObject
}
