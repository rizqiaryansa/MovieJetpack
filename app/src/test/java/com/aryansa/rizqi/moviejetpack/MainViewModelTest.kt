package com.aryansa.rizqi.moviejetpack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aryansa.rizqi.moviejetpack.domain.model.remote.Movie
import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieListResponse
import com.aryansa.rizqi.moviejetpack.data.repository.MovieRepositoryImpl
import com.aryansa.rizqi.moviejetpack.data.source.remote.MovieService
import com.aryansa.rizqi.moviejetpack.utils.*
import com.aryansa.rizqi.moviejetpack.utils.RxSchedulerRule
import com.aryansa.rizqi.moviejetpack.utils.observeOnce
import com.aryansa.rizqi.moviejetpack.utils.testObserver
import com.aryansa.rizqi.moviejetpack.presentation.viewmodel.MovieViewModel
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import kotlin.test.assertNotNull

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private lateinit var movieViewModel: MovieViewModel

    private lateinit var repository: MovieRepositoryImpl
    private val movieService = mock<MovieService>()

    @Mock
    private lateinit var mockObserver: Observer<ResultResponse>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MovieRepositoryImpl(movieService)
        movieViewModel = MovieViewModel(repository)
    }

    @After
    fun tearDown() {
        movieViewModel.state.removeObserver(mockObserver)
    }

    @Test
    fun `init set movie list to empty`() {
        val movies = movieViewModel.state.testObserver()

        Truth.assert_()
            .that(movies.observedValues).isEmpty()
    }

    @Test
    fun `check size movie list`() {

        val responseMovie = MovieListResponse(
            mutableListOf(
                Movie(
                    originalTitle = "Fast & Furious Presents: Hobbs & Shaw",
                    voteAverage = 6.5,
                    releaseDate = "2019-08-01"
                )
            )
        )

        whenever(movieService.getMovie(BuildConfig.TMDB_API_KEY)).thenReturn(Single.just(responseMovie))
        movieViewModel.getMovies(MovieType.MOVIE)

        movieViewModel.state.observeOnce {
            when(it) {
                is ResultResponse.Success -> {
                    assertNotNull(it.data)
                    Truth.assert_().that(it.data?.results?.size).isEqualTo(1)
                }
            }
        }
    }

    @Test
    fun `check size tv list`() {

        val responseTv = MovieListResponse(
            mutableListOf(
                Movie(
                    originalName = "The Flash",
                    voteAverage = 6.6,
                    firstAirDate = "2014-10-07"
                )
            )
        )

        whenever(movieService.getTvShow(BuildConfig.TMDB_API_KEY)).thenReturn(Single.just(responseTv))
        movieViewModel.getMovies(MovieType.TV)

        movieViewModel.state.observeOnce {
            when(it) {
                is ResultResponse.Success -> {
                    assertNotNull(it.data)
                    Truth.assert_().that(it.data?.results?.size).isEqualTo(1)
                }
            }
        }
    }

    @Test
    fun `fetch movies error from api service`() {

        val responseMovie = Throwable("Error Movie")

        whenever(movieService.getMovie(BuildConfig.TMDB_API_KEY)).thenReturn(Single.error(responseMovie))

        movieViewModel.getMovies(MovieType.MOVIE)

        movieViewModel.state.observeOnce {
            when(it) {
                is ResultResponse.Failure -> {
                    movieService.getMovie(BuildConfig.TMDB_API_KEY).test().apply {
                        assertError(it.throwable)
                    }
                }
            }
        }
    }

    @Test
    fun `check item list tv not null from repository`() {

        val responseMovie = Throwable("Error TV")

        whenever(movieService.getTvShow(BuildConfig.TMDB_API_KEY)).thenReturn(Single.error(responseMovie))

        movieViewModel.getMovies(MovieType.TV)

        movieViewModel.state.observeOnce {
            when(it) {
                is ResultResponse.Failure -> {
                    movieService.getTvShow(BuildConfig.TMDB_API_KEY).test().apply {
                        assertError(it.throwable)
                    }
                }
            }
        }
    }

}