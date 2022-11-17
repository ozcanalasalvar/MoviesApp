package com.example.moviesapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


@SuppressLint("NewApi", "SimpleDateFormat")
fun String?.reformatDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    return formatter.format(parser.parse(this))
}