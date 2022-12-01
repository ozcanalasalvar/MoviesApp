package com.ozcanalasalvar.moviesapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.ozcanalasalvar.moviesapp.data.Movie


class MoviesDiffCallBack : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}