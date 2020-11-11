package tech.nilu.wallets

import org.web3j.crypto.Credentials
import java.math.BigInteger

data class FullWallet(
    val credentials: Credentials?,
    val balance: BigInteger = BigInteger.ZERO
)
