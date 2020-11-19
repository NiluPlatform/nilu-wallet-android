package tech.nilu.main.ui

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.nilu.base.result.Error
import tech.nilu.base.result.Success
import tech.nilu.domain.usecases.wallet.CreateWalletUseCase
import tech.nilu.domain.usecases.wallet.GetAllWalletsWithContractsUseCase

class MainViewModel @ViewModelInject constructor(
    private val app: Application,
    private val createWalletUseCase: CreateWalletUseCase,
    private val getAllWalletsWithContractsUseCase: GetAllWalletsWithContractsUseCase
) : AndroidViewModel(app) {

    fun onInit() {
        viewModelScope.launch {
            when (val result = getAllWalletsWithContractsUseCase(
                GetAllWalletsWithContractsUseCase.Params(1, app.filesDir, "1234567890")
            )) {
                is Success -> println(result.data)
                is Error -> result.throwable.printStackTrace()
            }
        }
    }
}
