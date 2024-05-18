package com.example.movieapptask.bindingAdapters

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat


@BindingAdapter("setImageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {

    val circularProgressDrawable = CircularProgressDrawable(imageView.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .format(DecodeFormat.PREFER_RGB_565)
        .into(imageView)
}


@BindingAdapter("setImageSrc")
fun setImageUrl(
    imageView: ImageView,
    @DrawableRes
    src: Int
) {
    imageView.setImageResource(src)
}