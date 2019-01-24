package com.tp.orchid.ui.adapters.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {

    val requestOption = RequestOptions()

    Glide.with(imageView.context)
        .load(url)
        .apply(requestOption)
        .into(imageView)
}