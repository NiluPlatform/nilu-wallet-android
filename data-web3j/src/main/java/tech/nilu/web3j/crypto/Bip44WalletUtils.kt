package tech.nilu.web3j.crypto

import org.web3j.crypto.CipherException
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.WalletUtils
import java.io.File
import java.io.IOException

object Bip44WalletUtils {

    @Throws(CipherException::class, IOException::class)
    fun generateBip44Wallet(lang: Bip39Locale?, password: String?, destinationDirectory: File?): Bip44Wallet {
        val wallet = Bip44Wallet(lang, null)
        val key = wallet.createKey("M/44H/60H/0H/0/0")
        val privateKey = ECKeyPair.create(key.priv)
        val walletFile = WalletUtils.generateWalletFile(password, privateKey, destinationDirectory, false)
        wallet.filename = walletFile
        return wallet
    }

    fun loadBip44Credentials(mnemonic: String?): Credentials {
        val wallet = Bip44Wallet("", mnemonic)
        val dk = wallet.createKey("M/44H/60H/0H/0/0")
        return Credentials.create(dk.priv.toString(16))
    }
}
