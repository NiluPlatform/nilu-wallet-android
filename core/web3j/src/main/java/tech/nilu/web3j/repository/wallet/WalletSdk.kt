package tech.nilu.web3j.repository.wallet

import androidx.collection.LongSparseArray
import org.web3j.crypto.WalletUtils
import tech.nilu.data.dao.ContractInfoDao
import tech.nilu.data.dao.WalletDao
import tech.nilu.data.entity.Wallet
import tech.nilu.web3j.entity.HardWallet
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

class WalletSdk @Inject constructor(
    private val walletDao: WalletDao,
    private val contractInfoDao: ContractInfoDao
) {

    val wallets by lazy { LongSparseArray<HardWallet>() }

    suspend fun insert(wallet: Wallet) =
        walletDao.insert(wallet)

    suspend fun getWallet(id: Long) =
        walletDao.getWallet(id)

    fun getWalletsObservable(networkId: Long) =
        walletDao.getWalletsObservable(networkId)

    suspend fun update(wallet: Wallet) =
        walletDao.update(wallet)

    fun add(id: Long, wallet: HardWallet) {
        wallets.putIfAbsent(id, wallet)
    }

    suspend fun delete(wallet: Wallet) {
        contractInfoDao.deleteContractInfos(wallet.id)
        walletDao.delete(wallet)
        wallets.remove(wallet.id)
    }

    suspend fun getHardWallet(wallet: Wallet, parent: File, password: String): HardWallet {
        var data = wallets[wallet.id]
        if (data?.credentials == null) {
            val credentials = WalletUtils.loadCredentials(password, File(parent, wallet.path))
            data = HardWallet(wallet, credentials)
            add(wallet.id, data)
        }
        return data
    }

    suspend fun getKeystoreContent(path: String, parent: File): String {
        val file = File(parent, path)
        val content = ByteArray(file.length().toInt())
        FileInputStream(file).buffered().use { it.read(content) }
        return String(content)
    }
}
