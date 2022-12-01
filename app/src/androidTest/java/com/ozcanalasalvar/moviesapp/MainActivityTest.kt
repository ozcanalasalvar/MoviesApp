package com.ozcanalasalvar.moviesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ozcanalasalvar.moviesapp.util.NetworkMonitor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun show_popup_when_connection_fails() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        networkMonitor.setConnected(false)
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.network_error)))
    }
}