package tech.nilu.main.ui

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tech.nilu.base.result.Success
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.entity.contractinfo.ContractObject
import tech.nilu.domain.entity.wallet.WalletContractsObject
import tech.nilu.domain.invoke
import tech.nilu.domain.usecases.erc20.GetContractBalanceUseCase
import tech.nilu.domain.usecases.network.GetActiveNetworkUseCase
import tech.nilu.domain.usecases.wallet.GetAllWalletsWithContractsUseCase

class MainViewModel @ViewModelInject constructor(
    private val app: Application,
    private val getContractBalanceUseCase: GetContractBalanceUseCase,
    private val getAllWalletsWithContractsUseCase: GetAllWalletsWithContractsUseCase,
    private val getActiveNetworkUseCase: GetActiveNetworkUseCase
) : AndroidViewModel(app) {

    val activeNetwork: LiveData<NetworkObject?> = liveData {
        getActiveNetworkUseCase()
            .collect { result ->
                when (result) {
                    is Success -> emit(result.data)
                    else -> emit(null)
                }
            }
    }
    val wallets = liveData {
        when (val result = getAllWalletsWithContractsUseCase(GetAllWalletsWithContractsUseCase.Params(1, app.filesDir, "1234567890"))) {
            is Success -> emit(result.data)
            is Error -> emit(emptyList<WalletContractsObject>())
        }
    }
    val balance = wallets.switchMap {
        liveData {
            when (val result = getContractBalanceUseCase(GetContractBalanceUseCase.Params(4, fakeContract))) {
                is Success -> emit(result.data)
                else -> emit(null)
            }
        }
    }

    private val fakeContract = ContractObject(0, 0, "0x4f2a8a241972a0106a152cd9549c42760d9bf9b6", "", "", "ERC20")

    fun onInit() {
        viewModelScope.launch {
        }
    }
}
