package tech.nilu.web3j.mapper

import tech.nilu.base.mapper.Mapper
import tech.nilu.data.mapper.ContractInfoMapper
import tech.nilu.domain.entity.wallet.WalletContractsObject
import tech.nilu.web3j.entity.WalletContracts
import javax.inject.Inject

class WalletContractsMapper @Inject constructor(
    private val contractsMapper: ContractInfoMapper
) : Mapper<WalletContracts, WalletContractsObject> {

    override suspend fun map(from: WalletContracts): WalletContractsObject = with(from) {
        WalletContractsObject(
            id = wallet.id,
            name = wallet.name,
            path = wallet.path,
            mnemonic = wallet.mnemonic,
            networkId = wallet.networkId,
            credentials = credentials,
            contracts = contracts.map { contractsMapper.map(it) },
            balance = balance
        )
    }
}
