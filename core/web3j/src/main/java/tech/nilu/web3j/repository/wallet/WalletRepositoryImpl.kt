package tech.nilu.web3j.repository.wallet

import tech.nilu.domain.repository.WalletRepository
import tech.nilu.web3j.Web3jApiClient
import java.math.BigInteger
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val client: Web3jApiClient
) : WalletRepository {

    override suspend fun getBalance(address: String): BigInteger? =
        client.getBalance(address)

    override suspend fun getBalances(addresses: List<String>): List<BigInteger> =
        client.getBalances(addresses)

    override suspend fun getTotalBalance(addresses: List<String>): BigInteger =
        client.getTotalBalance(addresses)
}
