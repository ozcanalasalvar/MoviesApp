package com.example.moviesapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.Movie

class MovieSliderAdapter(private var movies: ArrayList<Movie>) : PagerAdapter() {

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        //Create inflater
        val inflater = LayoutInflater.from(container.context)

        //inflate root view
        val root = inflater.inflate(R.layout.slider_layout, null)

        //Define the views
        val imageView = root.findViewById<ImageView>(R.id.img_movie)
        val textTitle = root.findViewById<TextView>(R.id.tv_movie_title)
        val textDescription = root.findViewById<TextView>(R.id.tv_movie_overview)

        val movie = movies[position]

        textTitle.text = movie.title
        textDescription.text = movie.overview

        Glide
            .with(imageView.context)
            .load(movie.image)
            .centerCrop()
            .into(imageView)

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

    fun notifyDataChanges(movieList: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()

    }
}
