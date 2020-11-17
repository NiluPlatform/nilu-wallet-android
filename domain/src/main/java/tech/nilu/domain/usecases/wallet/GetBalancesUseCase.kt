package tech.nilu.domain.usecases.wallet

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.thread.Dispatcher
import java.math.BigInteger
import javax.inject.Inject

/**
 * Created by navid.eghbali on 11/17/20.
 */

class GetBalancesUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : UseCase<List<String>, List<BigInteger>>(dispatcher.io) {

    override suspend fun execute(params: List<String>): List<BigInteger> =
        repository.getBalances(addresses = params)
}
