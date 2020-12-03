package tech.nilu.web3j.repository.erc20

import tech.nilu.data.dao.ContractInfoDao
import tech.nilu.data.mapper.ContractInfoMapper
import tech.nilu.data.mapper.ContractObjectMapper
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.repository.ERC20Repository
import tech.nilu.web3j.Web3jApiClient
import java.math.BigInteger
import javax.inject.Inject

class ERC20RepositoryImpl @Inject constructor(
    private val client: Web3jApiClient,
    private val contractInfoDao: ContractInfoDao,
    private val contractInfoMapper: ContractInfoMapper,
    private val contractObjectMapper: ContractObjectMapper
) : ERC20Repository {

    override suspend fun fetchBasicContract(walletId: Long, contract: ContractObject): ContractObject {
        if (!contract.types.contains("ERC20") && !contract.types.contains("ERC20Basic"))
            throw IllegalStateException("Not a ERC20 token!")

        val basicContract = client.fetchBasicContract(walletId, contract.address)
        val result = basicContract.copy(
            id = contractInfoDao.insert(basicContract),
            walletId = contract.walletId,
            image = contract.image,
            types = contract.types
        )
        return contractInfoMapper.map(result)
    }

    override suspend fun fetchERC20Contract(walletId: Long, contract: ContractObject): ContractObject {
        if (!contract.types.contains("ERC20") && !contract.types.contains("ERC20Basic"))
            throw IllegalStateException("Not a ERC20 token!")

        val erc20Contract = client.fetchERC20Contract(walletId, contract.address)
        val result = erc20Contract.copy(
            walletId = contract.walletId,
            name = contract.name,
            image = contract.image,
            types = contract.types,
            symbol = contract.symbol
        )
        return contractInfoMapper.map(result)
    }

    override suspend fun contractBalance(walletId: Long, contract: ContractObject): BigInteger? {
        if (!contract.types.contains("ERC20") && !contract.types.contains("ERC20Basic"))
            throw IllegalStateException("Not a ERC20 token!")

        return client.contractBalance(walletId, contract.address)
    }

    override suspend fun contractSymbol(walletId: Long, contract: ContractObject): String? {
        if (!contract.types.contains("ERC20") && !contract.types.contains("ERC20Basic"))
            throw IllegalStateException("Not a ERC20 token!")

        return client.contractSymbol(walletId, contract.address)
    }

    override suspend fun saveContracts(walletId: Long, contracts: List<ContractObject>) {
        contractInfoDao.deleteContractInfos(walletId)
        client.contractsSymbol(walletId, contracts.map { it.address })
            .forEach { (address, symbol) ->
                contracts.firstOrNull { it.address == address }?.let {
                    contractInfoDao.insert(contractObjectMapper.map(it.copy(symbol = symbol)))
                }
            }
    }
}
