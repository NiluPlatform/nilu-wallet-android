package tech.nilu.web3j.entity

import org.web3j.crypto.Credentials
import tech.nilu.data.entity.Wallet
import java.math.BigInteger

data class HardWallet(
    val wallet: Wallet,
    val credentials: Credentials,
    val balance: BigInteger = BigInteger.ZERO
)
