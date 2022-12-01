package com.ozcanalasalvar.moviesapp.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ozcanalasalvar.moviesapp.adapters.MoviePagerAdapter
import com.ozcanalasalvar.moviesapp.adapters.MovieSliderAdapter
import com.ozcanalasalvar.moviesapp.data.Movie
import com.ozcanalasalvar.moviesapp.databinding.MoviesScreenLayoutBinding
import com.ozcanalasalvar.moviesapp.util.showFailurePopup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesScreen : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MoviesScreenLayoutBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val movieSliderAdapter: MovieSliderAdapter = MovieSliderAdapter(arrayListOf()) {
            navigateDetail(it)
        }
        binding.tabDots.setupWithViewPager(binding.slider, true)
        binding.slider.adapter = movieSliderAdapter


        val pagingAdapter = MoviePagerAdapter {
            navigateDetail(it)
        }
        binding.rvAllMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAllMovies.adapter = pagingAdapter

        collectTrendMovies(movieSliderAdapter)
        collectNowPlayingMovies(pagingAdapter)
        observeError()
        return binding.root
    }

    private fun navigateDetail(movie: Movie) {
        val action = MoviesScreenDirections.actionMoviesScreenToMovieDetailScreen(movie.id)
        requireView().findNavController().navigate(action)
    }

    private fun collectTrendMovies(adapter: MovieSliderAdapter) {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.notifyDataChanges(it)
        }
    }


    private fun collectNowPlayingMovies(adapter: MoviePagerAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingDataFlow?.collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner) {
            view?.showFailurePopup(it)
        }
    }
}