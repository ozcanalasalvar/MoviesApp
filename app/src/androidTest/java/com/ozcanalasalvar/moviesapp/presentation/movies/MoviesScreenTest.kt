package com.ozcanalasalvar.moviesapp.presentation.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ozcanalasalvar.moviesapp.util.FakeMovieService
import com.ozcanalasalvar.moviesapp.R
import com.ozcanalasalvar.moviesapp.launchFragmentInHiltContainer
import com.google.common.truth.Truth.assertThat
import com.ozcanalasalvar.moviesapp.presentation.movies.MoviesScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MoviesScreenTest {


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
    fun display_movie_list_success() {
        launchFragmentInHiltContainer<MoviesScreen>()

        onView(withId(R.id.rv_all_movies)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertThat(recyclerView.adapter?.itemCount).isGreaterThan(0)
        }
    }

    @Test
    fun show_fail_message_when_request_fails() {
        FakeMovieService.shouldThrowException(true)
        launchFragmentInHiltContainer<MoviesScreen>()

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(ViewAssertions.matches(ViewMatchers.withText("Test Exception")))
    }

    @Test
    fun click_recyclerview_item_navigate_detail_screen() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<MoviesScreen>() {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.rv_all_movies))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        verify(navController).navigate(
            MoviesScreenDirections.actionMoviesScreenToMovieDetailScreen(1)
        )

    }

    @Test
    fun click_pager_item_navigate_detail_screen() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<MoviesScreen>() {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(allOf(withId(R.id.sliderRoot), isDisplayed())).perform(click());

        verify(navController).navigate(
            MoviesScreenDirections.actionMoviesScreenToMovieDetailScreen(1)
        )

    }

//    @Test
//    fun click_pager_item_navigate_detail_screen() {
//        // Create a TestNavHostController
//        val navController = TestNavHostController(
//            ApplicationProvider.getApplicationContext())
//
//       launchFragmentInHiltContainer<MoviesScreen>(){
//            navController.setGraph(R.navigation.navigation)
//
//            // Make the NavController available via the findNavController() APIs
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        // Verify that performing a click changes the NavController’s state
//
//        onView(allOf(withId(R.id.sliderRoot), isDisplayed())).perform(click());
//
//        sleep(2000L)
//        assertThat(navController.currentDestination?.id).isEqualTo(R.id.movieDetailScreen)
//    }

}