package com.example.androidflowexample.core.base

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.androidflowexample.R

typealias InflateActivityView<T> = (LayoutInflater) -> T

typealias InflateFragmentView<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

typealias Str = R.string

typealias Drw = R.drawable

typealias Clr = R.color


fun FragmentActivity.showDialog(dialogFragment: DialogFragment) {
    dialogFragment.show(supportFragmentManager, dialogFragment.tag)
}

fun Fragment.getColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(requireContext(), colorId)
}

fun Activity.getColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Fragment.getDrawable(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(requireContext(), drawableRes)
}


fun Context.extGetColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

/**
 * use it with when, to add all remaining branches
 */
val <T> T.exhaustive: T
    get() = this

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}


