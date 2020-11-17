package tech.nilu.domain.repository

import java.math.BigInteger

interface WalletRepository {

    suspend fun getBalance(address: String): BigInteger?

    suspend fun getBalances(addresses: List<String>): List<BigInteger>

    suspend fun getTotalBalance(addresses: List<String>): BigInteger
}
