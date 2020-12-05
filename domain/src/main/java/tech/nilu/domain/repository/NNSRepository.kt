package tech.nilu.domain.repository

import tech.nilu.domain.entity.DomainInfoObject
import java.math.BigInteger

interface NNSRepository {

    suspend fun getGasPrice(): BigInteger?

    suspend fun resolveDomain(
        walletId: Long,
        domain: String
    ): String?

    suspend fun reverseResolution(walletId: Long): String

    suspend fun queryDomains(
        walletId: Long,
        node: String
    ): DomainInfoObject

    suspend fun registerDomain(
        walletId: Long,
        subnode: String,
        rootNode: String
    ): Boolean

    suspend fun releaseDomain(
        walletId: Long,
        domainToRelease: String
    ): Boolean
}
