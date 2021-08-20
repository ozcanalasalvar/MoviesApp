package com.example.moviesapp.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.ui.base.BaseFragment
import com.example.moviesapp.utils.loadMovieImageFromUrl
import javax.inject.Inject

class DetailFragment : BaseFragment<FragmentMovieDetailBinding, DetailViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var movieId: Int = 0
    private var url: String? = null

    override fun getViewModel(): DetailViewModel =
        ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

    override fun getLayoutResId(): Int = R.layout.fragment_movie_detail

    override fun onInject() {
        super.onInject()
        getApplicationComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt("selectedMovie")
            url = it.getString("movieUrl")
        }

    }

    override fun init() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        binding.imgMovie.transitionName = movieId.toString()
        binding.texyMovieTitle.transitionName = "text$movieId"

        mViewModel.getMovieDetail(movieId)
        binding.imgMovie.loadMovieImageFromUrl(url)

        binding.executePendingBindings()

        observeDetailData()
    }

    private fun observeDetailData() {
        mViewModel.detailLiveData.observe(viewLifecycleOwner, Observer {
            binding.movie = it
        })

        mViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            showError(it)
        })
    }

}