package tech.nilu.main.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.nilu.base.result.Error
import tech.nilu.base.result.Success
import tech.nilu.domain.usecases.network.GetActiveNetworkUseCase
import tech.nilu.domain.usecases.network.GetNetworkUseCase
import tech.nilu.domain.usecases.wallet.GetWalletUseCase

class MainViewModel @ViewModelInject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val getNetworkUseCase: GetNetworkUseCase,
    private val getActiveNetworkUseCase: GetActiveNetworkUseCase
) : ViewModel() {

    fun onInit() {
        viewModelScope.launch {
            when (val result = getWalletUseCase(2)) {
                is Success -> println(result.data.id)
                is Error -> result.throwable.printStackTrace()
            }
            when (val result = getNetworkUseCase(4)) {
                is Success -> println(result.data.id)
                is Error -> result.throwable.printStackTrace()
            }
        }
    }
}
