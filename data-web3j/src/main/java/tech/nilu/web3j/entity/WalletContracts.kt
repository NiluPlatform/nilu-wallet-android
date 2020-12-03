package tech.nilu.web3j.entity

import org.web3j.crypto.Credentials
import tech.nilu.data.entity.ContractInfo
import tech.nilu.data.entity.Wallet
import java.math.BigInteger

data class WalletContracts(
    val wallet: Wallet,
    val contracts: List<ContractInfo>,
    val credentials: Credentials,
    val balance: BigInteger = BigInteger.ZERO
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as WalletContracts
        if (other.wallet.id != wallet.id) return false

        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
