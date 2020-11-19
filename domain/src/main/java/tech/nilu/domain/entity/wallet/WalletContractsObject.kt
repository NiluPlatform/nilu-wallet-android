package tech.nilu.domain.entity.wallet

import org.web3j.crypto.Credentials
import tech.nilu.domain.entity.BaseObject
import tech.nilu.domain.entity.contractinfo.ContractObject
import java.math.BigInteger

/**
 * Created by navid.eghbali on 11/18/20.
 */

data class WalletContractsObject(
    override val id: Long,
    val name: String,
    val path: String,
    val mnemonic: String?,
    val networkId: Long,
    val credentials: Credentials,
    val contracts: List<ContractObject>,
    var balance: BigInteger = BigInteger.ZERO
) : BaseObject
