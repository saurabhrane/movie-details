package me.saurabhrane.mvp.utils

import android.widget.ImageView
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import me.saurabhrane.mvp.BuildConfig
import me.saurabhrane.mvp.R

/**
 * Consists of all binding adapters used in the layout file
 */
@BindingAdapter("loadSmallImage")
fun loadSmallImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.load(BuildConfig.SMALL_IMAGE_URL+it) {
            placeholder(R.drawable.dummy_image)
            error(R.drawable.ic_broken_image)
        }
    } ?: imageView.setImageResource(R.drawable.ic_broken_image)
}

@BindingAdapter("loadCircularImage")
fun loadCircularImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.load(BuildConfig.SMALL_IMAGE_URL+it) {
            placeholder(R.drawable.dummy_image)
            error(R.drawable.ic_broken_image)
            transformations(CircleCropTransformation())
        }
    } ?: imageView.setImageResource(R.drawable.ic_broken_image)
}

@BindingAdapter("loadLargeImage")
fun loadLargeImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.load(BuildConfig.LARGE_IMAGE_URL+it) {
            placeholder(R.drawable.dummy_image)
            error(R.drawable.ic_broken_image)
        }
    } ?: imageView.setImageResource(R.drawable.ic_broken_image)
}

@BindingAdapter("setRating")
fun setRating(ratingBar: RatingBar, vote: Double) {
    ratingBar.rating = (vote / 2).toFloat()
}