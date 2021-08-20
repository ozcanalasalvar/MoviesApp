package com.example.moviesapp.ui.main.adapter.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.data.model.list.MovieModel
import com.example.moviesapp.databinding.MovieCellLayoutBinding
import com.example.moviesapp.ui.main.adapter.MovieClickListener

class MovieListAdapter(private var movieList: ArrayList<MovieModel>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private var listener: MovieClickListener? = null

    fun setOnMovieClickListener(clickListener: MovieClickListener) {
        listener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<MovieCellLayoutBinding>(
            inflater,
            R.layout.movie_cell_layout,
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position], listener)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = movieList.size

    fun notifyDataChanges(movies: List<MovieModel>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }


    fun notifyItemInsert(movies: List<MovieModel>) {
        val lastIndex = itemCount
        movieList.addAll(movies)
        notifyItemRangeInserted(lastIndex, movies.size);
    }

}