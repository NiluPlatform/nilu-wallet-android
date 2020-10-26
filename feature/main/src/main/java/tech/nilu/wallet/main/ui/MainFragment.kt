package tech.nilu.wallet.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tech.nilu.wallet.databinding.BindableFragment
import tech.nilu.wallet.main.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : BindableFragment<FragmentMainBinding>() {
    private val viewModel: MainViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(binding: FragmentMainBinding, savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(): MainFragment = MainFragment().apply { }
    }
}
