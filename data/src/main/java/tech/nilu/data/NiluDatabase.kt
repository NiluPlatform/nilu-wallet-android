package tech.nilu.data

import tech.nilu.data.dao.*

interface NiluDatabase {

    fun contractInfoDao(): ContractInfoDao

    fun destinationDao(): DestinationDao

    fun networkDao(): NetworkDao

    fun transactionDao(): TransactionDao

    fun walletDao(): WalletDao
}
