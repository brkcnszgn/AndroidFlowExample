package com.example.androidflowexample.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.androidflowexample.R
import com.example.androidflowexample.core.base.BaseFragment
import com.example.androidflowexample.core.base.Drw
import com.example.androidflowexample.core.base.setDivider
import com.example.androidflowexample.databinding.FragmentFirstBinding
import com.example.androidflowexample.ui.home.model.HomeUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentFirstBinding, HomeViewModel>(FragmentFirstBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    private val homeAdapter by lazy {
        HomeAdapter { item ->
            //no*op
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

    }

    override fun initView() {
        with(binding) {
            recyclerView.apply {
                adapter = homeAdapter
                setDivider(Drw.divider)
            }
        }
    }

    override fun logicProcess() {
        collect()
    }

    override fun clickListener() {

    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.homeUiState.collect { state ->
                        when (state) {
                            is HomeUiState.Error -> {
                                //show error
                            }
                            is HomeUiState.RefreshList -> {
                                homeAdapter.submitList(state.refreshMovieList)
                            }
                            is HomeUiState.Success -> {
                                homeAdapter.submitList(state.movieList)
                            }
                        }

                    }
                }
            }

        }
    }

}