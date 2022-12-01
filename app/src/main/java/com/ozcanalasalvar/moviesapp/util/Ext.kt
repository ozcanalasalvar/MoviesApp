package com.ozcanalasalvar.moviesapp.util

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun View.visible() {
    this.visibility = View.VISIBLE
}


fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}


fun View.showFailurePopup(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
        .apply {
            val sbView = this.view
            sbView.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_red_dark
                )
            )
        }.show()
}