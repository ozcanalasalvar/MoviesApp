package com.ozcanalasalvar.moviesapp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.ozcanalasalvar.moviesapp.domain.model.Movie

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoviePager(
    sliderMovies: List<Movie>?,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(256.dp),
            count = 5,
        ) { page ->
            sliderMovies?.get(page)?.let {
                MoviePagerCard(movie = it)
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .align(Alignment.BottomCenter),
            activeColor = Color(0xFFFFFFFF),
            spacing = 20.dp
        )
    }
}