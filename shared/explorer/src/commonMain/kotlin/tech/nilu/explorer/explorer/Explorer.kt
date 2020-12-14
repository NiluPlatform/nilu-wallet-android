package tech.nilu.explorer.explorer

import io.ktor.client.request.*
import io.ktor.http.*
import tech.nilu.explorer.Result
import tech.nilu.explorer.entity.Transaction

internal interface Explorer {

    val baseUrl: String
    val canGetTransactions: Boolean
    val canGetTokenTransactions: Boolean

    suspend fun getTransactions(address: String): Result<List<Transaction>>

    suspend fun getTokenTransactions(tokenAddress: String, walletAddress: String): Result<List<Transaction>>

    fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(baseUrl)
            encodedPath = path
        }
    }
}


