package tech.nilu.wallets

import androidx.collection.LongSparseArray
import kotlinx.coroutines.withContext
import org.web3j.crypto.WalletUtils
import tech.nilu.thread.Dispatcher
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletSdk @Inject constructor(
    private val ioDispatcher: Dispatcher
) {
    val wallets = LongSparseArray<FullWallet>()

    fun addWallet(id: Long, wallet: FullWallet) {
        wallets.putIfAbsent(id, wallet)
    }

    suspend fun getFullWallet(id: Long, path: String, parent: File, password: String): FullWallet = withContext(ioDispatcher.io) {
        var wallet = wallets[id]
        if (wallet?.credentials == null) {
            val credentials = WalletUtils.loadCredentials(password, File(parent, path))
            wallet = FullWallet(credentials)
            addWallet(id, wallet)
        }
        wallet
    }

    suspend fun getKeystoreContent(path: String, parent: File) = withContext(ioDispatcher.io) {
        val file = File(parent, path)
        val content = ByteArray(file.length().toInt())
        try {
            val stream = FileInputStream(file)
            stream.read(content)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        String(content)
    }
}
