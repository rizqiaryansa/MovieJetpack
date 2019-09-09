package com.aryansa.rizqi.moviejetpack.adapter

import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.databinding.BindingAdapter
import com.aryansa.rizqi.moviejetpack.BuildConfig
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.extension.gone
import com.aryansa.rizqi.moviejetpack.extension.visible
import com.aryansa.rizqi.moviejetpack.util.AppConstant
import com.aryansa.rizqi.moviejetpack.util.GlideApp
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.facebook.shimmer.ShimmerFrameLayout

@BindingAdapter("setImageUrl")
fun AppCompatImageView.setImageUrl(imageUrl: String?) {
    val url = BuildConfig.IMAGE_BASE_URL + AppConstant.IMAGE_PATH + imageUrl
    val radius = context.resources.getDimensionPixelSize(R.dimen._8dp)
    GlideApp.with(context)
        .load(url)
        .transform(FitCenter(), RoundedCorners(radius))
        .into(this)
}

@BindingAdapter("setShimmer")
fun ShimmerFrameLayout.setShimmer(boolean: Boolean) {
    if(boolean) {
        startShimmer()
        visible()
    } else {
        stopShimmer()
        gone()
    }
}

@BindingAdapter("setRating")
fun AppCompatRatingBar.setRating(value: Double) {
    rating = value.toFloat()
}

@BindingAdapter("setLoading")
fun ProgressBar.setLoading(boolean: Boolean) {
    if(boolean) {
        visible()
    } else {
        gone()
    }
}