package com.example.moviesapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.moviesapp.R

@BindingAdapter("android:downloadUrl")
fun loadImage(view: ImageView, url: String?) {
    view.downLoadFromUrl(url)
}

fun ImageView.downLoadFromUrl(url: String?) {
    Glide.with(context).load(url).into(this)
}


@BindingAdapter("android:downloadMovieImage")
fun loadMovieImage(view: ImageView, url: String?) {
    val domain = view.context.resources.getString(R.string.image_domain)
    view.downLoadFromUrl(domain + url)
}

fun ImageView.loadMovieImageFromUrl(url: String?) {
    val domain = context.resources.getString(R.string.image_domain)
    Glide.with(context).load(domain + url).into(this)
}

@BindingAdapter("android:textDateFormatted")
fun loadImage(view: TextView, date: String?) {
    view.setDateFormattedText(date)
}

fun TextView.setDateFormattedText(date: String?) {
    date?.let { text = DateFormatter.formatDateString(it) }
}