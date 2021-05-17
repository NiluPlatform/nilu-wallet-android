package tech.nilu.main.ui

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tech.nilu.base.result.Success
import tech.nilu.domain.entity.NetworkObject
import tech.nilu.domain.entity.wallet.WalletContractsObject
import tech.nilu.domain.invoke
import tech.nilu.domain.usecases.network.AddNetworksUseCase
import tech.nilu.domain.usecases.network.ObserveActiveNetworkUseCase
import tech.nilu.domain.usecases.network.ObserveNetworksCountUseCase
import tech.nilu.domain.usecases.wallet.GetAllWalletsWithContractsUseCase

class MainViewModel @ViewModelInject constructor(
    private val app: Application,
    private val observeActiveNetworkUseCase: ObserveActiveNetworkUseCase,
    private val getAllWalletsWithContractsUseCase: GetAllWalletsWithContractsUseCase,
    private val observeNetworksCountUseCase: ObserveNetworksCountUseCase,
    private val addNetworksUseCase: AddNetworksUseCase
) : AndroidViewModel(app) {

    private val count: LiveData<Long?> = liveData {
        observeNetworksCountUseCase()
            .collect { result ->
                when (result) {
                    is Success -> emit(result.data)
                    else -> emit(null)
                }
            }
    }
    private val countObserver = Observer<Long?> {
        viewModelScope.launch {
            if (it == 0L) {
                addNetworksUseCase(NETWORKS)
            }
        }
    }

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
                when (val result = getAllWalletsWithContractsUseCase(GetAllWalletsWithContractsUseCase.Params(it.id, app.filesDir, "1234567890"))) {
                    is Success -> emit(result.data)
                    is Error -> emit(emptyList<WalletContractsObject>())
                }
            }

        } ?: MutableLiveData(emptyList())
    }

    fun onInit() {
        viewModelScope.launch {
            count.observeForever(countObserver)
        }
    }

    override fun onCleared() {
        super.onCleared()
        count.removeObserver(countObserver)
    }

    companion object {
        private val NETWORKS: List<NetworkObject> = listOf(
            NetworkObject(1, "Nilu", "https://walletapi.nilu.tech", 1, "Ɲ", 512, "https://walletapi.nilu.tech/"),
            NetworkObject(2, "Ethereum", "https://mainnet.infura.io", 0, "Ξ", 1, "https://api.ethplorer.io/"),
            NetworkObject(3, "Ropsten (Test)", "https://ropsten.infura.io", 0, "Ξ", 3, ""),
            NetworkObject(4, "Pirl", "https://rpc.pirl.minerpool.net", 0, "PIRL", 3125659152, "http://devpool.nilu.tech/pirl/"),
            NetworkObject(5, "Ether-1", "https://rpc.ether1.org", 0, "ETHO", 1313114, "https://walletapi.ether1.org/")
        )
    }
}
