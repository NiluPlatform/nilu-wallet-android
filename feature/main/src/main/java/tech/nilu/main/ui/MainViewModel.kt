package tech.nilu.main.ui

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    fun onInit() {
        viewModelScope.launch {
        }
    }
}
