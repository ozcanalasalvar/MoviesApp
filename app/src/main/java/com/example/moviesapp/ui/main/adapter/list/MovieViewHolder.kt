package com.example.moviesapp.ui.main.adapter.list

import android.annotation.SuppressLint
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.model.list.MovieModel
import com.example.moviesapp.databinding.MovieCellLayoutBinding
import com.example.moviesapp.ui.main.adapter.MovieClickListener
import com.example.moviesapp.utils.DateFormatter
import com.example.moviesapp.utils.downLoadFromUrl

class MovieViewHolder(private val binding: MovieCellLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(movie: MovieModel, listener: MovieClickListener?) {

        binding.textMovieTitle.text =
            movie.title + " (" + DateFormatter.getYearFromDateString(movie.release_date) + ")"

        binding.textMovieDescription.text = movie.overview

        binding.textMovieDate.text = DateFormatter.formatDateString(movie.release_date)

        binding.imgMovie.transitionName = "" + movie.id

        binding.imgMovie.transitionName = "" + movie.id
        binding.textMovieTitle.transitionName = "text" + movie.id

        val url =
            binding.imgMovie.context.resources.getString(R.string.image_domain) + movie.backdrop_path
        binding.imgMovie.downLoadFromUrl(url)



        binding.root.setOnClickListener {
            listener?.onMovieClicked(movie, binding.imgMovie, binding.textMovieTitle)
        }

        binding.executePendingBindings()
    }
}