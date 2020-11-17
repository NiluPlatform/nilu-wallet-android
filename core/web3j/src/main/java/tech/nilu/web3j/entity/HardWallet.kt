package tech.nilu.web3j.entity

import org.web3j.crypto.Credentials
import tech.nilu.data.entity.Wallet
import java.math.BigInteger

data class HardWallet(
    val wallet: Wallet,
    val credentials: Credentials,
    val balance: BigInteger = BigInteger.ZERO
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as HardWallet
        if (other.wallet.id != wallet.id) return false

        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
