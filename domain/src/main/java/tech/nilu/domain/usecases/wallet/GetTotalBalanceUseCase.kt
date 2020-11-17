package tech.nilu.domain.usecases.wallet

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.thread.Dispatcher
import java.math.BigInteger
import javax.inject.Inject

/**
 * Created by navid.eghbali on 11/17/20.
 */

class GetTotalBalanceUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : UseCase<List<String>, BigInteger>(dispatcher.io) {

    override suspend fun execute(params: List<String>): BigInteger =
        repository.getTotalBalance(addresses = params)
}
