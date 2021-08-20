package com.example.moviesapp.ui.main.adapter.slider

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.model.list.MovieModel
import com.example.moviesapp.ui.main.adapter.MovieClickListener
import com.example.moviesapp.utils.DateFormatter
import com.example.moviesapp.utils.downLoadFromUrl

class SliderAdapter(private var movies: ArrayList<MovieModel>) : PagerAdapter() {

    private var listener: MovieClickListener? = null

    fun setOnMovieClickListener(clickListener: MovieClickListener) {
        listener = clickListener
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        //Create inflater
        val inflater = LayoutInflater.from(container.context)

        //inflate root view
        val root = inflater.inflate(R.layout.slider_layout, null)

        //Define the views
        val imageView = root.findViewById<ImageView>(R.id.img_movie)
        val textTitle = root.findViewById<TextView>(R.id.text_movie_title)
        val textDescription = root.findViewById<TextView>(R.id.text_movie_description)

        val movie = movies[position]

        textTitle.text =
            movie.title + " (" + DateFormatter.getYearFromDateString(movie.release_date) + ")"
        textDescription.text = movie.overview

        imageView.transitionName = "" + movie.id
        textTitle.transitionName = "text" + movie.id

        val url = imageView.context.resources.getString(R.string.image_domain) + movie.backdrop_path
        imageView.downLoadFromUrl(url)


        //Set up click
        root.setOnClickListener {
            listener?.onMovieClicked(movie, imageView, textTitle)
        }

        //add container to root view
        container.addView(root)

        return root
    }

    override fun getCount(): Int = if (movies.size > 5) 5 else movies.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun notifyDataChanges(movieList: List<MovieModel>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()

    }
}