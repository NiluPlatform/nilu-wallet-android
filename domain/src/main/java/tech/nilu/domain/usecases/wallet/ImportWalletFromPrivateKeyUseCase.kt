package tech.nilu.domain.usecases.wallet

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.thread.Dispatcher
import java.io.File
import javax.inject.Inject

class ImportWalletFromPrivateKeyUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : UseCase<ImportWalletFromPrivateKeyUseCase.Params, Long>(dispatcher.io) {

    override suspend fun execute(params: Params): Long = repository.importWalletFromPrivateKey(
        privateKey = params.privateKey,
        walletName = params.walletName,
        password = params.password,
        destinationDir = params.destinationDir,
        networkId = params.networkId
    )

    data class Params(
        val privateKey: String,
        val walletName: String,
        val password: String,
        val destinationDir: File,
        val networkId: Long
    )
}
