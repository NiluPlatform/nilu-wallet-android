package tech.nilu.domain.entity.contractinfo

import tech.nilu.domain.entity.BaseObject
import java.math.BigInteger

/**
 * Created by navid.eghbali on 11/18/20.
 */

data class ContractObject(
    override val id: Long,
    val walletId: Long,
    val address: String,
    val name: String,
    val image: String,
    val types: String,
    var symbol: String? = null,
    var balance: BigInteger = BigInteger.ZERO,
    var tokenName: String? = null,
    var tokenSymbol: String? = null,
    var tokenTotalSupply: BigInteger? = null,
    var tokenDecimals: BigInteger? = null,
    var tokenRate: BigInteger? = null,
    var tokenIsPayable: Boolean? = null
) : BaseObject
