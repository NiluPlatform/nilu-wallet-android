package tech.nilu.web3core

import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle

fun Web3Sdk.saveContractsSingle(): Single<Web3Sdk.Result<Boolean>> {
    return rxSingle {
        saveContracts()
    }
}
