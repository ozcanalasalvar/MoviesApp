package com.ozcanalasalvar.moviesapp.presentation.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ozcanalasalvar.moviesapp.R
import com.ozcanalasalvar.moviesapp.domain.model.MovieDetail
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
                        onBAckPressed = {
                            requireView().findNavController().popBackStack()
                        },
                        detail = (uiState as MovieDetailUIState.Success).data
                    )
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Detail(detail: MovieDetail, modifier: Modifier = Modifier, onBAckPressed: () -> Unit) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFEEEEEE))
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 75.dp, end = 15.dp, bottom = 15.dp)
            ) {


                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {


                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(150.dp),
                        shape = RoundedCornerShape(2.dp)
                    ) {

                        GlideImage(
                            detail.image,
                            contentDescription = "movie image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp)
                    ) {


                        Text(
                            text = detail.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )


                        Spacer(modifier = Modifier.height(5.dp))

                        Row {
                            (0..(detail.voteAverage.toInt())).forEach { _ ->
                                Image(
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = "start",
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .size(10.dp)
                                )
                            }
                        }


                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = detail.genres,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = detail.releaseDate,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                        )

                    }

                }
            }


            Text(
                text = detail.overview,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF565656),
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 30.dp)
            )




            Text(
                text = "Cast",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF565656),
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 25.dp)
            )


            LazyRow(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(detail.castings ?: listOf()) { _, item ->
                    Card(
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp)
                            .padding(start = 10.dp, top = 20.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        GlideImage(
                            item.profileImage,
                            contentDescription = "movie image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }



        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "back arrow",
            Modifier
                .padding(top = 35.dp)
                .size(35.dp)
                .align(Alignment.TopStart)
                .clickable {
                    onBAckPressed()
                }
        )


    }
}