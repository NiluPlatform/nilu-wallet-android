package tech.nilu.web3j.repository.erc20

import org.web3j.protocol.core.methods.response.Log
import java.math.BigInteger

sealed class TransferEvent {

    data class Response(
        val log: Log,
        val from: String,
        val to: String,
        val value: BigInteger
    )
}
