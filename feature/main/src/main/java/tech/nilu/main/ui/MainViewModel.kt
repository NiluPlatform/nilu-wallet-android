package tech.nilu.main.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.nilu.base.result.Error
import tech.nilu.base.result.Success
import tech.nilu.domain.usecases.wallet.GetWalletUseCase

class MainViewModel @ViewModelInject constructor(
    private val getWalletUseCase: GetWalletUseCase
) : ViewModel() {

    fun onInit() {
        viewModelScope.launch {
            when (val result = getWalletUseCase(2)) {
                is Success -> println(result.data.id)
                is Error -> result.throwable.printStackTrace()
            }
        }
    }
}
