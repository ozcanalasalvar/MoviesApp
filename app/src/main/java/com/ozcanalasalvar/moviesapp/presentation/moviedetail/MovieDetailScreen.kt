package com.ozcanalasalvar.moviesapp.presentation.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ozcanalasalvar.moviesapp.presentation.component.Detail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailScreen : Fragment() {

    private val viewModel: MovieDetailViewModel by viewModels()

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
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.observeAsState()
                if (uiState is MovieDetailUIState.Success)
                    Detail(
                        modifier = Modifier.fillMaxSize(),
                        detail = (uiState as MovieDetailUIState.Success).data,
                        onBAckPressed = {
                            requireView().findNavController().popBackStack()
                        },
                    )
            }
        }

    }
}
