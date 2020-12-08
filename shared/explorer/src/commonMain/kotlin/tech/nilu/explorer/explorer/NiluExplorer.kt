package tech.nilu.explorer.explorer

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.kodein.di.instance
import tech.nilu.explorer.Error
import tech.nilu.explorer.Result
import tech.nilu.explorer.Success
import tech.nilu.explorer.di.kodein
import tech.nilu.explorer.entity.Transaction

class NiluExplorer : Explorer {

    private val json: Json by kodein.di.instance()
    private val httpClient: HttpClient by kodein.di.instance()

    override val baseUrl: String = "https://walletapi.nilu.tech/"
    override val canGetTransactions: Boolean = true
    override val canGetTokenTransactions: Boolean = true

    override suspend fun getTransactions(address: String): Result<List<Transaction>> {
        val response = httpClient.get<HttpResponse> {
            apiUrl("explorer/transactions/$address/0/50")
        }
        return when (response.status.value) {
            in HttpStatusCode.OK.value..HttpStatusCode.MultipleChoices.value -> {
                Success(json.decodeFromString(ListSerializer(Transaction.serializer()), response.readText()))
            }
            else -> Error(IOException(response.status.description))
        }
    }

    override suspend fun getTokenTransactions(tokenAddress: String, walletAddress: String): Result<List<Transaction>> {
        val response = httpClient.get<HttpResponse> {
            apiUrl("explorer/transfers/$tokenAddress/$walletAddress/0/50")
        }
        return when (response.status.value) {
            in HttpStatusCode.OK.value..HttpStatusCode.MultipleChoices.value -> {
                Success(json.decodeFromString(ListSerializer(Transaction.serializer()), response.readText()))
            }
            else -> Error(IOException(response.status.description))
        }
    }
}
