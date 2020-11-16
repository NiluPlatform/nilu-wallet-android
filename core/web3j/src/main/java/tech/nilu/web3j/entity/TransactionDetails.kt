package tech.nilu.web3j.entity

import org.web3j.protocol.core.methods.response.Transaction
import org.web3j.protocol.core.methods.response.TransactionReceipt

data class TransactionDetails(
    val transaction: Transaction?,
    val receipt: TransactionReceipt?
)
