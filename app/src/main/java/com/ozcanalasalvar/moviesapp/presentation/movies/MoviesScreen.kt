package com.ozcanalasalvar.moviesapp.presentation.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ozcanalasalvar.moviesapp.domain.model.Movie
import com.ozcanalasalvar.moviesapp.presentation.component.MovieCard
import com.ozcanalasalvar.moviesapp.presentation.component.MoviePager
import com.ozcanalasalvar.moviesapp.util.showFailurePopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesScreen : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeError()
        return ComposeView(requireContext()).apply {
            setContent {
                val pagingFilms = viewModel.pagingDataFlow!!.collectAsLazyPagingItems()
                val pagerState = rememberPagerState()
                val sliderMovies by viewModel.movies.observeAsState()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        MoviePager(
                            sliderMovies = sliderMovies,
                            pagerState = pagerState,
                            onClick = { movie ->
                                navigateDetail(movie)
                            })
                    }


                    itemsIndexed(pagingFilms) { _, item ->
                        item?.let {
                            MovieCard(it, onClick = { movie ->
                                navigateDetail(movie)
                            })
                        }
                    }

                }
            }
        }

    }

    private fun navigateDetail(movie: Movie) {
        val action = MoviesScreenDirections.actionMoviesScreenToMovieDetailScreen(movie.id)
        requireView().findNavController().navigate(action)
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner) {
            view?.showFailurePopup(it)
        }
    }
}
