package com.example.fakestore.ui.bindingAdapters

import android.view.View
import android.view.animation.Animation
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState


@BindingAdapter("goneIfTrue")
fun goneIfTrue(view: View, boolean: Boolean) {
    view.visibility = if (boolean) View.GONE else View.VISIBLE
}

@BindingAdapter("setAnimation")
fun setAnimation(view: View, anim: Animation) {
    view.apply {
        animation = anim
    }
}

@BindingAdapter("setLoadingState")
fun bindLoadingState(view: View,loadState: LoadState){
    view.isVisible = loadState is LoadState.Loading
}
@BindingAdapter("setErrorState")
fun bindErrorState(view: View,loadState: LoadState){
    view.isVisible = loadState is LoadState.Error
}
