package com.example.androidflowexample.core.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.example.androidflowexample.R


private const val TAG = "ProgressBar"

class ProgressBar : DialogFragment() {

    companion object {
        fun newInstance(): ProgressBar {
            val fragment = ProgressBar()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var isShown: Boolean = false

    fun isProgressShown(): Boolean = isShown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.fullScreenDialog)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_progress_loader, container, false)
    }

    fun show(manager: FragmentManager) {
        show(manager, TAG)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager.findFragmentByTag(TAG) == null) {
            manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
            this.isShown = true
        }
    }

    fun hide() {
        if (isShown) {
            this.dismissAllowingStateLoss()
            this.isShown = false
        }
    }

}