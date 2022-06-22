package com.example.androidflowexample.core.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VB : ViewBinding, AC : Activity>(
    private val inflateFragmentView: InflateFragmentView<VB>
) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!


    protected val mActivity: AC
        get() = activity as AC

    fun showProgress() {
        ProgressBar.newInstance().show(childFragmentManager)
    }

    fun hideProgress() {
        ProgressBar.newInstance().hide()
    }

    abstract fun init()
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
        init()
        logicProcess()
        clickListener()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}