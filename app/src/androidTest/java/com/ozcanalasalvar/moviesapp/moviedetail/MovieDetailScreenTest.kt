package com.ozcanalasalvar.moviesapp.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ozcanalasalvar.moviesapp.launchFragmentInHiltContainer
import com.ozcanalasalvar.moviesapp.util.FakeMovieService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import com.ozcanalasalvar.moviesapp.R
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MovieDetailScreenTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        FakeMovieService.shouldThrowException(false)
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<MovieDetailScreen>() {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.iv_back)).perform(click())
        //pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun display_movieDetail_success() {
        launchFragmentInHiltContainer<MovieDetailScreen>(fragmentArgs = bundleOf("movieId" to 0))

        onView(withId(R.id.tv_movie_title)).check(matches(withText("title")))
        onView(withId(R.id.tvOverview)).check(matches(withText("movie overview")))

        onView(withId(R.id.rvCasts)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            Truth.assertThat(recyclerView.adapter?.itemCount).isGreaterThan(0)
        }
    }


    @Test
    fun show_error_on_detail_fail() {
        FakeMovieService.shouldThrowException(true)
        launchFragmentInHiltContainer<MovieDetailScreen>(fragmentArgs = bundleOf("movieId" to 0))

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Test Exception")))

    }
}