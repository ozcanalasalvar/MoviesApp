package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.model.cast.CastingDto
import com.example.moviesapp.data.model.detail.Genre
import com.example.moviesapp.data.model.detail.MovieDetailDto
import com.example.moviesapp.data.model.list.MovieDto
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MovieMapperTest {

    lateinit var movieDto: MovieDto
    lateinit var movieDetailDto: MovieDetailDto
    lateinit var castingDto: CastingDto

    @Before
    fun setUp() {
        movieDto = MovieDto(
            id = 1,
            title = "title",
            original_title = "original title",
            poster_path = "path",
            overview = "film overview",
            release_date = "2016-08-03",
            video = false,
            original_language = "US",
            popularity = 1234.0,
            vote_count = 1,
            vote_average = 1.0,
            backdrop_path = "path2",
        )


        castingDto = CastingDto(
            adult = false,
            cast_id = 1,
            character = "character",
            credit_id = "28",
            gender = 2,
            id = 1,
            known_for_department = "Acting",
            name = "Edward Norton",
            order = 0,
            original_name = "Edward Norton",
            popularity = 7.861,
            profile_path = "/5XBzD5WuTyVQZeS4VI25z2moMeY.jpg",
        )

        movieDetailDto = MovieDetailDto(
            adult = false,
            backdrop_path = "backDrop",
            budget = 28,
            genres = listOf(Genre(1, "1")),
            homepage = "homePage",
            id = 1,
            imdb_id = "tt0137523",
            original_language = "US",
            original_title = "original title",
            overview = "movie overview",
            popularity = 28.0,
            poster_path = "posterPath",
            release_date = "2016-08-03",
            revenue = 1,
            runtime = 100,
            status = "status",
            tagline = "tagline",
            title = "title",
            video = false,
            vote_average = 5.0,
            vote_count = 288,
        )
    }

    @Test
    fun `map MovieDto , return Movie model`() {
        val movie = movieDto.toMovie()

        assertThat(movie.id).isEqualTo(movieDto.id)
        assertThat(movie.title).isEqualTo(movieDto.title)
        assertThat(movie.releaseDate).isEqualTo("03.08.2016")
        assertThat(movie.overview).isEqualTo(movieDto.overview)
        assertThat(movie.voteCount).isEqualTo(movieDto.vote_count)
        assertThat(movie.image).endsWith(movieDto.poster_path)
    }

    @Test
    fun `map MovieDto and if poster_path is null , return image string end with backdrop_path`() {
        val dto = movieDto.copy(poster_path = null, backdrop_path = "path")
        val movie = dto.toMovie()
        assertThat(movie.image).endsWith(dto.backdrop_path)
    }


    @Test
    fun `map MovieDto and if poster_path and backdrop_path are null , return null`() {
        val dto = movieDto.copy(poster_path = null, backdrop_path = null)
        val movie = dto.toMovie()

        assertThat(movie.image).isEqualTo(null)
    }


    @Test
    fun `map MovieDto and if title is null , return original title`() {
        val dto = movieDto.copy(title = null, original_title = "original title")
        val movie = dto.toMovie()

        assertThat(movie.title).endsWith(movieDto.original_title)
    }


    @Test
    fun `map MovieDto and if title and original title are null , return not found`() {
        val dto = movieDto.copy(title = null, original_title = null)
        val movie = dto.toMovie()

        assertThat(movie.title).isEqualTo("No title found")
    }


    @Test
    fun `map MovieDetailDto , return MovieDetail model`() {
        val movieDetail = movieDetailDto.toMovieDetail(listOf(castingDto))

        assertThat(movieDetail.id).isEqualTo(movieDetailDto.id)
        assertThat(movieDetail.title).isEqualTo(movieDetailDto.title)
        assertThat(movieDetail.releaseDate).isEqualTo("03.08.2016")
        assertThat(movieDetail.overview).isEqualTo(movieDetailDto.overview)
        assertThat(movieDetail.voteCount).isEqualTo(movieDetailDto.vote_count)
        assertThat(movieDetail.image).endsWith(movieDetailDto.poster_path)

        val casting = movieDetail.castings?.first()
        assertThat(casting).isNotEqualTo(null)
        assertThat(casting!!.id).isEqualTo(castingDto.id)
    }

    @Test
    fun `map MovieDetailDto and if poster_path is null , return image string end with backdrop_path`() {
        val dto = movieDetailDto.copy(poster_path = null, backdrop_path = "path")
        val movie = dto.toMovieDetail(null)
        assertThat(movie.image).isEqualTo("https://image.tmdb.org/t/p/w500" + dto.backdrop_path)
    }


    @Test
    fun `map MovieDetailDto and if poster_path and backdrop_path are null , return null`() {
        val dto = movieDetailDto.copy(poster_path = null, backdrop_path = null)
        val movie = dto.toMovieDetail(null)

        assertThat(movie.image).isEqualTo(null)
    }


    @Test
    fun `map MovieDetailDto and if title is null , return original title`() {
        val dto = movieDetailDto.copy(title = null, original_title = "original title")
        val movie = dto.toMovieDetail(null)

        assertThat(movie.title).isEqualTo(movieDto.original_title)
    }


    @Test
    fun `map MovieDetailDto and if title and original title are null , return not found`() {
        val dto = movieDetailDto.copy(title = null, original_title = null)
        val movie = dto.toMovieDetail(null)

        assertThat(movie.title).isEqualTo("No title found")
    }


    @Test
    fun `map CastingDto, name is null , returns original name as a name`() {
        val dto = castingDto.copy(name = null)
        val casting = dto.toCasting()

        assertThat(casting.name).isEqualTo(dto.original_name)
    }

    @Test
    fun `map CastingDto , returns profile picture ends with profile_path`() {
        val casting = castingDto.toCasting()

        assertThat(casting.profileImage).isEqualTo("https://image.tmdb.org/t/p/w500" + castingDto.profile_path)
    }


}