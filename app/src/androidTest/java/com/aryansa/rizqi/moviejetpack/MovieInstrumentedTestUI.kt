package com.aryansa.rizqi.moviejetpack

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.aryansa.rizqi.moviejetpack.util.EspressoIdlingResource
import com.aryansa.rizqi.moviejetpack.view.activity.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieInstrumentedTestUI {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun checkItemListMovie() {
        val listMovie = onView(allOf(withId(R.id.rvMovies)))
        listMovie.check(matches(isDisplayed()))
        listMovie.perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(6))
        listMovie.perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click())
        )
    }

    @Test
    fun checkRightTabViewPager() {
        val movieTab = onView(withId(R.id.view_pager2))
        movieTab.check(matches(isDisplayed()))
        movieTab.perform(swipeRight())
        onView(withText(R.string.tv_show)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun checkLeftTabViewPager() {
        val movieTab = onView(withId(R.id.view_pager2))
        movieTab.check(matches(isDisplayed()))
        movieTab.perform(swipeRight())
        movieTab.perform(swipeLeft())
        onView(withText(R.string.movie)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun checkToolbarImageDetail() {
        val detailMovie = onView(withId(R.id.rvMovies))
        detailMovie.check(matches(isDisplayed()))
        detailMovie.perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        detailMovie.perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click())
        )

        onView(withId(R.id.toolbar_image)).check(matches(isDisplayed()))
    }


}