package tech.nilu.main.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.nilu.base.result.Error
import tech.nilu.base.result.Success
import tech.nilu.domain.usecases.network.GetActiveNetworkUseCase
import tech.nilu.domain.usecases.network.GetNetworkUseCase
import tech.nilu.domain.usecases.transaction.GetTransactionDetailsUseCase
import tech.nilu.domain.usecases.wallet.GetWalletUseCase

class MainViewModel @ViewModelInject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val getNetworkUseCase: GetNetworkUseCase,
    private val getTransactionDetailsUseCase: GetTransactionDetailsUseCase,
    private val getActiveNetworkUseCase: GetActiveNetworkUseCase
) : ViewModel() {

    fun onInit() {
        viewModelScope.launch {
            when (val response = getTransactionDetailsUseCase("0xd214c0f5c4af3241f1a7dac31a4b77042df976993b1e28f1f23e4f849948dc2b")) {
                is Success -> println(response.data.value)
                is Error -> response.throwable.printStackTrace()
            }
        }
    }
}
