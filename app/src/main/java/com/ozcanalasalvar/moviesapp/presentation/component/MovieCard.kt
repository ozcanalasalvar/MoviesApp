package com.ozcanalasalvar.moviesapp.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ozcanalasalvar.moviesapp.R
import com.ozcanalasalvar.moviesapp.domain.model.Movie


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieCard(movie: Movie, onClick: (Movie) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(120.dp)
            .clickable {
                onClick(movie)
            }
    ) {


        Card(
            modifier = Modifier
                .width(90.dp)
                .height(120.dp),
            shape = RoundedCornerShape(2.dp)
        ) {

            GlideImage(
                movie.image,
                contentDescription = "movie image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }


        ConstraintLayout(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .fillMaxHeight()
        ) {
            val (content, date) = createRefs()
            Column(
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .width(IntrinsicSize.Max)
                    .padding(7.dp)
            ) {
                Text(
                    text = movie.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(5.dp))

                Row {
                    (0..(movie.voteAverage.toInt())).forEach { _ ->
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
                    text = movie.overview,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }


            Text(
                text = movie.releaseDate,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 7.dp, top = 10.dp)
                    .constrainAs(date) {
                        bottom.linkTo(parent.bottom)
                        top.linkTo(content.bottom)
                    }
            )
        }

    }
}