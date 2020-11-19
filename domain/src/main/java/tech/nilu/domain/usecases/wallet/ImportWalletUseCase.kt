package tech.nilu.domain.usecases.wallet

import tech.nilu.domain.UseCase
import tech.nilu.domain.repository.WalletRepository
import tech.nilu.thread.Dispatcher
import java.io.File
import javax.inject.Inject

/**
 * Created by navid.eghbali on 11/18/20.
 */

class ImportWalletUseCase @Inject constructor(
    private val repository: WalletRepository,
    dispatcher: Dispatcher
) : UseCase<ImportWalletUseCase.Params, Long>(dispatcher.io) {

    override suspend fun execute(params: Params): Long = repository.importWallet(
        mnemonic = params.mnemonic,
        name = params.name,
        password = params.password,
        destinationDir = params.destinationDir,
        networkId = params.networkId
    )

    data class Params(
        val mnemonic: String,
        val name: String,
        val password: String,
        val destinationDir: File,
        val networkId: Long
    )
}
