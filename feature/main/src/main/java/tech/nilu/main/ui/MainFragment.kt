package tech.nilu.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tech.nilu.databinding.BindableFragment
import tech.nilu.main.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : BindableFragment<FragmentMainBinding>() {
    private val viewModel: MainViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(binding: FragmentMainBinding, savedInstanceState: Bundle?) {
        viewModel.onInit()
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment().apply { }
    }
}
