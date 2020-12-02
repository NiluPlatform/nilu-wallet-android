package tech.nilu.domain.repository

import tech.nilu.domain.entity.contractinfo.ContractObject
import java.math.BigInteger

interface ERC20Repository {

    suspend fun fetchBasicContract(
        walletId: Long,
        contract: ContractObject
    ): ContractObject

    suspend fun fetchERC20Contract(
        walletId: Long,
        contract: ContractObject
    ): ContractObject

    suspend fun contractBalance(
        walletId: Long,
        contract: ContractObject
    ): BigInteger?

    suspend fun contractSymbol(
        walletId: Long,
        contract: ContractObject
    ): String?

    suspend fun saveContracts(
        walletId: Long,
        contracts: List<ContractObject>
    )
}
