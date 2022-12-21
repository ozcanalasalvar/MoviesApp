package com.ozcanalasalvar.moviesapp.presentation.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ozcanalasalvar.moviesapp.presentation.adapters.CastingAdapter
import com.ozcanalasalvar.moviesapp.domain.model.MovieDetail
import com.ozcanalasalvar.moviesapp.databinding.MovieDetailScreenLayoutBinding
import com.ozcanalasalvar.moviesapp.util.showFailurePopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailScreen : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()

    private lateinit var binding: MovieDetailScreenLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val movieId = it.getInt("movieId")
            viewModel.init(movieId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailScreenLayoutBinding.inflate(inflater, container, false)
        context ?: return binding.root


        val adapter = CastingAdapter()
        renderUi(adapter)

        binding.rvCasts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCasts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCasts.adapter = adapter

        binding.ivBack.setOnClickListener {
            requireView().findNavController().popBackStack()
        }

        return binding.root
    }

    private fun renderUi(adapter: CastingAdapter) {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieDetailUIState.Error -> {
                    requireView().showFailurePopup(state.message)
                }
                MovieDetailUIState.Loading -> {

                }
                is MovieDetailUIState.Success -> {
                    fillUI(state.data)
                    state.data.castings?.let {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun fillUI(detail: MovieDetail) {
        with(binding) {
            tvMovieTitle.text = detail.title
            tvDuration.text = detail.duration
            tvGenre.text = detail.genres
            tvOverview.text = detail.overview
            tvPopularity.text = detail.popularity.toString()
            tvVoteAverage.text = detail.voteAverage.toString()
            tvVoteCount.text = detail.voteCount.toString()

            Glide
                .with(requireContext())
                .load(detail.image)
                .centerCrop()
                .into(ivMovie)
        }
    }


}