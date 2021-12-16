package io.github.kabirnayeem99.awesomeCatApp.ui.views.bindingAdapters

import android.view.View

import androidx.databinding.BindingAdapter


@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}