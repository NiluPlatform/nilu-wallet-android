package tech.nilu.main.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor() : ViewModel() {

    fun onInit() {
        viewModelScope.launch {
        }
    }
}
