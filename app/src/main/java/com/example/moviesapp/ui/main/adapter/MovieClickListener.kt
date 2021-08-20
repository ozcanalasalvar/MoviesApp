package com.example.moviesapp.ui.main.adapter

import android.widget.ImageView
import android.widget.TextView
import com.example.moviesapp.data.model.list.MovieModel

interface MovieClickListener {

    fun onMovieClicked(movie: MovieModel, imageView: ImageView, textViewTitle: TextView)
}