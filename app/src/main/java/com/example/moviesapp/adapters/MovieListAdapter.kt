package com.example.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.data.Movie
import com.example.moviesapp.databinding.MovieListItemCellBinding

class MovieListAdapter : ListAdapter<Movie, RecyclerView.ViewHolder>(MoviesDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            MovieListItemCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(getItem(position))
    }


    class MovieViewHolder(private val binding: MovieListItemCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide
                .with(binding.imgMovie.context)
                .load(movie.image)
                .centerCrop()
                .into(binding.imgMovie);
        }
    }

}

