package tech.nilu.domain.repository

import tech.nilu.domain.entity.wallet.WalletContractsObject
import java.io.File
import java.math.BigInteger

interface WalletRepository {

    suspend fun getBalance(address: String): BigInteger?

    suspend fun getBalances(addresses: List<String>): List<BigInteger>

    suspend fun getTotalBalance(addresses: List<String>): BigInteger

    suspend fun createWallet(
        name: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long

    suspend fun importWallet(
        mnemonic: String,
        name: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long

    suspend fun importWalletFromKeyStore(
        keyStoreJson: String,
        keyStorePassword: String,
        walletName: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long

    suspend fun importWalletFromPrivateKey(
        privateKey: String,
        walletName: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long

    suspend fun loadWalletsWithContracts(
        selectedNetworkId: Long,
        parent: File,
        password: String
    ): List<WalletContractsObject>

    suspend fun loadWalletWithContracts(
        walletId: Long,
        parent: File,
        password: String
    ): WalletContractsObject?

    suspend fun loadWalletNoContracts(
        walletId: Long,
        parent: File,
        password: String
    ): WalletContractsObject?
}
