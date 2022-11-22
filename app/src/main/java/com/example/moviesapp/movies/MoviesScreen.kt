package com.example.moviesapp.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.adapters.MovieListAdapter
import com.example.moviesapp.adapters.MoviePagerAdapter
import com.example.moviesapp.databinding.MoviesScreenLayoutBinding
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
    ): View? {
        val binding = MoviesScreenLayoutBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = MovieListAdapter()
        subscribeUi(adapter)

        binding.rvTrendMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTrendMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendMovies.adapter = adapter


        val pagingAdapter = MoviePagerAdapter()
        binding.rvAllMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAllMovies.adapter = pagingAdapter
        collectUiState(pagingAdapter)

        return binding.root
    }

    private fun subscribeUi(adapter: MovieListAdapter) {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


    private fun collectUiState(adapter: MoviePagerAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getNowPlayingMovies().collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }
}