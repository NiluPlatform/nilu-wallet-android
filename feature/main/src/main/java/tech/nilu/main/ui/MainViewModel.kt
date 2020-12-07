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
import tech.nilu.domain.usecases.explorer.GetTransactionsUseCase
import tech.nilu.domain.usecases.network.ObserveActiveNetworkUseCase
import tech.nilu.domain.usecases.wallet.GetAllWalletsWithContractsUseCase

class MainViewModel @ViewModelInject constructor(
    private val app: Application,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val observeActiveNetworkUseCase: ObserveActiveNetworkUseCase,
    private val getAllWalletsWithContractsUseCase: GetAllWalletsWithContractsUseCase
) : AndroidViewModel(app) {

    val activeNetwork: LiveData<NetworkObject?> = liveData {
        observeActiveNetworkUseCase()
            .collect { result ->
                when (result) {
                    is Success -> emit(result.data)
                    else -> emit(null)
                }
            }
    }
    val wallets = activeNetwork.switchMap {
        it?.let {
            liveData {
                when (val result =
                    getAllWalletsWithContractsUseCase(GetAllWalletsWithContractsUseCase.Params(it.id, app.filesDir, "1234567890"))) {
                    is Success -> emit(result.data)
                    is Error -> emit(emptyList<WalletContractsObject>())
                }
            }

        } ?: MutableLiveData(emptyList())
    }
    val balance = wallets.switchMap {
        liveData {
            when (val result = getTransactionsUseCase("0xC9c2dBEC5AFA62520F02c46EA2F193525EB2751d")) {
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
