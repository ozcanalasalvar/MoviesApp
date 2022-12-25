package com.ozcanalasalvar.moviesapp.presentation.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ozcanalasalvar.moviesapp.util.FakeMovieService
import com.ozcanalasalvar.moviesapp.launchFragmentInHiltContainer
import com.ozcanalasalvar.moviesapp.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        FakeMovieService.shouldThrowException(false)
        hiltRule.inject()
    }

    @Test
    fun display_movie_list_success() {
        launchFragmentInHiltContainer<MoviesScreen>()

        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG)
            .onChildren()[1]
            .assert(hasText("title1"))
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

        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG)
            .onChildren()[1].performClick()

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

        composeTestRule.onNodeWithTag(MOVIE_LIST_TEST_TAG).onChildren()[0].performClick()

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