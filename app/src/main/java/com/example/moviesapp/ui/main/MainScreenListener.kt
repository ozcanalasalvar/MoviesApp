package com.example.moviesapp.ui.main

import com.example.moviesapp.data.model.list.MovieModel

interface MainScreenListener {

    fun updateSliderContent(movies: List<MovieModel>)

    fun updateListContent(movies: List<MovieModel>)

    fun insertListContent(movies: List<MovieModel>)

    fun onError(message: String)

    fun showLoading()

    fun hideLoading()

    fun resetScrollSate()


}