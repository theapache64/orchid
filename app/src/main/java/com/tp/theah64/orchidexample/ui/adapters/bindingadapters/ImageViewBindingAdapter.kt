package com.tp.theah64.orchidexample.ui.adapters.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tp.theah64.orchidexample.R

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {

    val requestOption = RequestOptions()
        .centerCrop()
        .error(R.drawable.ic_error_outline_primary_dark_100dp)
        .placeholder(R.drawable.ic_video_camera_primary_dark_100dp)

    Glide.with(imageView.context)
        .load(url)
        .apply(requestOption)
        .into(imageView)
}