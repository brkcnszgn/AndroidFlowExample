package com.example.androidflowexample.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.androidflowexample.core.utils.AuthError
import com.example.androidflowexample.core.utils.InternetConnectionError
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, ViewModel : BaseViewModel>(
    private val inflateFragmentView: InflateFragmentView<VB>
) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private var loadingDialog: LoadingDialog? = null

    abstract val viewModel: ViewModel

    protected open fun hideLoadingDialog() {
        loadingDialog?.dismissAllowingStateLoss()
        loadingDialog = null
    }

    protected open fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(
                args = LoadingDialog.LoadingModel(
                    "Yükleniyor...",
                    ""
                )
            )
        }
        val isAlreadyAdded = childFragmentManager.findFragmentByTag(LoadingDialog.TAG)
        if (isAlreadyAdded == null) {
            loadingDialog?.showNow(childFragmentManager, LoadingDialog.TAG)
        }
    }

    abstract fun initView()
    abstract fun logicProcess()
    abstract fun clickListener()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateFragmentView.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        logicProcess()
        clickListener()
        collectNetworkError()
        collectShowLoading()
    }

    private fun collectNetworkError() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.networkError.collect { error ->
                        when (error) {
                            is AuthError -> {

                            }
                            is UnknownError -> {

                            }
                            is InternetConnectionError -> {

                            }
                            else -> {

                            }

                        }
                    }
                }
            }

        }

    }

    private fun collectShowLoading() {
        lifecycleScope.launchWhenStarted {
            viewModel.showLoading.collect { show->
                if (show) {
                    showLoadingDialog()
                } else {
                    hideLoadingDialog()
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}