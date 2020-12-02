package tech.nilu.main.ui

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.nilu.domain.usecases.token.GetDeploymentFeeUseCase

class MainViewModel @ViewModelInject constructor(
    app: Application,
    private val getDeploymentFeeUseCase: GetDeploymentFeeUseCase
) : AndroidViewModel(app) {

    fun onInit() {
        viewModelScope.launch {
        }
    }
}
