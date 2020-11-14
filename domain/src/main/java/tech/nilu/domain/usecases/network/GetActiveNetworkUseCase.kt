package tech.nilu.domain.usecases.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tech.nilu.base.result.Result
import tech.nilu.domain.FlowUseCase
import tech.nilu.domain.model.NetworkObject
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class GetActiveNetworkUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : FlowUseCase<Unit, NetworkObject>(dispatcher.io) {

    override fun execute(params: Unit): Flow<Result<NetworkObject>> {
        return flow {}
    }
}
