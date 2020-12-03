package tech.nilu.web3j.repository.wallet

import tech.nilu.domain.entity.wallet.WalletContractsObject
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.web3j.Web3jApiClient
import tech.nilu.web3j.mapper.WalletContractsMapper
import java.io.File
import java.math.BigInteger
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val sdk: WalletSdk,
    private val client: Web3jApiClient,
    private val mapper: WalletContractsMapper
) : WalletRepository {

    override suspend fun getBalance(address: String): BigInteger? =
        client.getBalance(address)

    override suspend fun getBalances(addresses: List<String>): List<BigInteger> =
        client.getBalances(addresses)

    override suspend fun getTotalBalance(addresses: List<String>): BigInteger =
        client.getTotalBalance(addresses)

    override suspend fun createWallet(name: String, password: String, destinationDir: File, networkId: Long): Long =
        sdk.createWallet(name, password, destinationDir, networkId)

    override suspend fun importWallet(mnemonic: String, name: String, password: String, destinationDir: File, networkId: Long): Long =
        sdk.importWallet(mnemonic, name, password, destinationDir, networkId)

    override suspend fun importWalletFromKeyStore(
        keyStoreJson: String,
        keyStorePassword: String,
        walletName: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long = sdk.importWalletFromKeyStore(keyStoreJson, keyStorePassword, walletName, password, destinationDir, networkId)

    override suspend fun importWalletFromPrivateKey(
        privateKey: String,
        walletName: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long = sdk.importWalletFromPrivateKey(privateKey, walletName, password, destinationDir, networkId)

    override suspend fun loadWalletsWithContracts(selectedNetworkId: Long, parent: File, password: String): List<WalletContractsObject> =
        sdk.loadWalletsWithContracts(selectedNetworkId, parent, password)
            .map { mapper.map(it) }

    override suspend fun loadWalletWithContracts(walletId: Long, parent: File, password: String): WalletContractsObject? =
        sdk.loadWalletWithContracts(walletId, parent, password)?.let { mapper.map(it) }

    override suspend fun loadWalletNoContracts(walletId: Long, parent: File, password: String): WalletContractsObject? =
        sdk.loadWalletNoContracts(walletId, parent, password)?.let { mapper.map(it) }
}
