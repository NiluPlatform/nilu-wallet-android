package tech.nilu.web3j.repository.smartcontracts

import tech.nilu.domain.entity.DomainInfoObject
import tech.nilu.domain.repository.NNSRepository
import tech.nilu.web3j.Web3jApiClient
import tech.nilu.web3j.mapper.DomainInfoMapper
import java.math.BigInteger
import javax.inject.Inject

class NNSRepositoryImpl @Inject constructor(
    private val client: Web3jApiClient,
    private val mapper: DomainInfoMapper
) : NNSRepository {

    override suspend fun getGasPrice(): BigInteger? =
        client.getGasPrice()

    override suspend fun resolveDomain(walletId: Long, domain: String): String? =
        client.resolveDomain(walletId, domain)

    override suspend fun reverseResolution(walletId: Long): String =
        client.reverseResolution(walletId)

    override suspend fun queryDomains(walletId: Long, node: String): DomainInfoObject = mapper.map(
        client.queryDomains(walletId, node)
    )

    override suspend fun registerDomain(walletId: Long, subnode: String, rootNode: String): Boolean =
        client.registerDomain(walletId, subnode, rootNode)

    override suspend fun releaseDomain(walletId: Long, domainToRelease: String): Boolean =
        client.releaseDomain(walletId, domainToRelease)
}
