package tech.nilu.domain.usecases.nns

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.NNSRepository
import tech.nilu.thread.Dispatcher
import javax.inject.Inject

class ReverseResolveUseCase @Inject constructor(
    private val repository: NNSRepository,
    dispatcher: Dispatcher
) : UseCase<Long, String>(dispatcher.io) {

    override suspend fun execute(params: Long): String = repository.reverseResolution(walletId = params)
}
