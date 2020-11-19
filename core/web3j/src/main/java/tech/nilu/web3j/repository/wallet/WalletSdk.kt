package tech.nilu.web3j.repository.wallet

import androidx.collection.LongSparseArray
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import tech.nilu.data.dao.ContractInfoDao
import tech.nilu.data.dao.WalletDao
import tech.nilu.data.entity.Wallet
import tech.nilu.web3j.crypto.Bip39Locale
import tech.nilu.web3j.crypto.Bip44WalletUtils
import tech.nilu.web3j.entity.WalletContracts
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

class WalletSdk @Inject constructor(
    private val walletDao: WalletDao,
    private val contractInfoDao: ContractInfoDao
) {

    val wallets by lazy { LongSparseArray<WalletContracts>() }

    suspend fun insert(wallet: Wallet) =
        walletDao.insert(wallet)

    suspend fun getWallet(id: Long) =
        walletDao.getWallet(id)

    fun getWalletsObservable(networkId: Long) =
        walletDao.getWalletsObservable(networkId)

    suspend fun update(wallet: Wallet) =
        walletDao.update(wallet)

    fun add(id: Long, wallet: WalletContracts) {
        wallets.putIfAbsent(id, wallet)
    }

    suspend fun delete(wallet: Wallet) {
        contractInfoDao.deleteContractInfos(wallet.id)
        walletDao.delete(wallet)
        wallets.remove(wallet.id)
    }

    suspend fun createWallet(
        walletName: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long {
        val wallet = Bip44WalletUtils.generateBip44Wallet(Bip39Locale.ENGLISH, password, destinationDir)
        return insert(
            Wallet(
                id = 0L,
                name = walletName,
                path = wallet.filename,
                mnemonic = wallet.mnemonic,
                networkId = networkId
            )
        )
    }

    suspend fun importWallet(
        mnemonic: String,
        walletName: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long {
        val credentials = Bip44WalletUtils.loadBip44Credentials(mnemonic)
        val fileName = WalletUtils.generateWalletFile(password, credentials.ecKeyPair, destinationDir, false)
        return insert(
            Wallet(
                id = 0L,
                name = walletName,
                path = fileName,
                mnemonic = null,
                networkId = networkId
            )
        )
    }

    suspend fun importWalletFromKeyStore(
        keyStoreJson: String,
        keyStorePassword: String,
        walletName: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long {
        val file = File.createTempFile("json-", null)
        FileOutputStream(file).buffered().use { it.write(keyStoreJson.toByteArray()) }
        val credentials = WalletUtils.loadCredentials(keyStorePassword, file)
        val fileName = WalletUtils.generateWalletFile(password, credentials.ecKeyPair, destinationDir, false)
        return insert(
            Wallet(
                id = 0L,
                name = walletName,
                path = fileName,
                mnemonic = null,
                networkId = networkId
            )
        )
    }

    suspend fun importWalletFromPrivateKey(
        privateKey: String,
        walletName: String,
        password: String,
        destinationDir: File,
        networkId: Long
    ): Long {
        val credentials = Credentials.create(privateKey)
        val fileName = WalletUtils.generateWalletFile(password, credentials.ecKeyPair, destinationDir, false)
        return insert(
            Wallet(
                id = 0L,
                name = walletName,
                path = fileName,
                mnemonic = null,
                networkId = networkId
            )
        )
    }

    suspend fun loadWalletsWithContracts(
        selectedNetworkId: Long,
        parent: File,
        password: String
    ): List<WalletContracts> {
        val result = mutableListOf<WalletContracts>()
        val wallets = walletDao.getWallets(selectedNetworkId)
        wallets.forEach {
            coroutineScope {
                val hardWalletTask = async { getWalletNoContracts(it, parent, password) }
                val contractsTask = async { contractInfoDao.getContractInfos(it.id) }
                val hardWallet = hardWalletTask.await()
                result.add(WalletContracts(hardWallet.wallet, contractsTask.await(), hardWallet.credentials))
            }
        }
        return result
    }

    suspend fun loadWalletWithContracts(
        walletId: Long,
        parent: File,
        password: String
    ): WalletContracts? {
        val wallet = getWallet(walletId) ?: return null
        return coroutineScope {
            val hardWalletTask = async { getWalletNoContracts(wallet, parent, password) }
            val contractsTask = async { contractInfoDao.getContractInfos(walletId) }
            val hardWallet = hardWalletTask.await()
            WalletContracts(hardWallet.wallet, contractsTask.await(), hardWallet.credentials)
        }
    }

    suspend fun loadWalletNoContracts(
        walletId: Long,
        parent: File,
        password: String
    ): WalletContracts? {
        val wallet = getWallet(walletId) ?: return null
        return getWalletNoContracts(wallet, parent, password)
    }

    private suspend fun getWalletNoContracts(
        wallet: Wallet,
        parent: File,
        password: String
    ): WalletContracts {
        var data = wallets[wallet.id]
        if (data?.credentials == null) {
            val credentials = WalletUtils.loadCredentials(password, File(parent, wallet.path))
            data = WalletContracts(wallet, emptyList(), credentials)
            add(wallet.id, data)
        }
        return data
    }

    suspend fun getKeystoreContent(
        path: String,
        parent: File
    ): String {
        val file = File(parent, path)
        val content = ByteArray(file.length().toInt())
        FileInputStream(file).buffered().use { it.read(content) }
        return String(content)
    }
}
