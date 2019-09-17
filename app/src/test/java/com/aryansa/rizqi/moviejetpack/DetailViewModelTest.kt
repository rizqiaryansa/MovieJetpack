package com.aryansa.rizqi.moviejetpack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.aryansa.rizqi.moviejetpack.data.source.local.dao.MovieDao
import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.data.repository.FavoriteRepository
import com.aryansa.rizqi.moviejetpack.data.repository.FavoriteRepositoryImpl
import com.aryansa.rizqi.moviejetpack.data.repository.MovieRepositoryImpl
import com.aryansa.rizqi.moviejetpack.data.source.remote.MovieService
import com.aryansa.rizqi.moviejetpack.utils.MovieType
import com.aryansa.rizqi.moviejetpack.utils.ResultResponse
import com.aryansa.rizqi.moviejetpack.utils.RxSchedulerRule
import com.aryansa.rizqi.moviejetpack.utils.observeOnce
import com.aryansa.rizqi.moviejetpack.utils.testObserver
import com.aryansa.rizqi.moviejetpack.presentation.viewmodel.DetailMovieViewModel
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
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(JUnit4::class)
class DetailViewModelTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private lateinit var movieViewModel: DetailMovieViewModel

    private lateinit var repository: MovieRepositoryImpl
    private lateinit var favoriteRepository: FavoriteRepository
    private val movieService = mock<MovieService>()
    private val mockDao = mock<MovieDao>()

    @Mock
    private lateinit var mockObserver: Observer<ResultResponse>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MovieRepositoryImpl(movieService)
        favoriteRepository = FavoriteRepositoryImpl(mockDao)
        movieViewModel = DetailMovieViewModel(repository, favoriteRepository)
    }

    @After
    fun tearDown() {
        movieViewModel.state.removeObserver(mockObserver)
    }

    @Test
    fun `init set detail movie list to empty`() {
        val movies = movieViewModel.state.testObserver()

        Truth.assert_()
            .that(movies.observedValues).isEmpty()
    }

    @Test
    fun `check data detail movie`() {
        val responseDetailMovie = MovieDetailResponse(
            originalTitle = "Fast & Furious Presents: Hobbs & Shaw",
            voteAverage = 6.5,
            releaseDate = "2019-08-01"
        )

        whenever(movieService.getMovieDetail(384018, BuildConfig.TMDB_API_KEY)).thenReturn(
            Single.just(
                responseDetailMovie
            )
        )
        movieViewModel.getMovieDetail(MovieType.MOVIE, 384018)

        movieViewModel.state.observeOnce {
            when (it) {
                is ResultResponse.SuccessDetail -> {
                    assertNotNull(it.data)
                    assertEquals(it.data?.originalTitle, responseDetailMovie.originalTitle)
                    assertEquals(it.data?.voteAverage, responseDetailMovie.voteAverage)
                    assertEquals(it.data?.releaseDate, responseDetailMovie.releaseDate)
                }
            }
        }
    }

    @Test
    fun `check data detail tv`() {
        val responseDetailTv = MovieDetailResponse(
            nameTv = "The Flash",
            voteAverage = 6.6,
            firstAirDate = "2014-10-07"
        )

        whenever(movieService.getTvDetail(60735, BuildConfig.TMDB_API_KEY)).thenReturn(
            Single.just(
                responseDetailTv
            )
        )
        movieViewModel.getMovieDetail(MovieType.TV, 60735)

        movieViewModel.state.observeOnce {
            when (it) {
                is ResultResponse.SuccessDetail -> {
                    assertNotNull(it.data)
                    assertEquals(it.data?.nameTv, responseDetailTv.nameTv)
                    assertEquals(it.data?.voteAverage, responseDetailTv.voteAverage)
                    assertEquals(it.data?.firstAirDate, responseDetailTv.firstAirDate)
                }
            }
        }
    }

    @Test
    fun `fetch detail movies error from api service`() {

        val responseDetailMovie = Throwable("Error Detail Movie")

        whenever(movieService.getMovieDetail(384018, BuildConfig.TMDB_API_KEY)).thenReturn(Single.error(responseDetailMovie))

        movieViewModel.getMovieDetail(MovieType.MOVIE, 384018)

        movieViewModel.state.observeOnce {
            when(it) {
                is ResultResponse.Failure -> {
                    movieService.getMovieDetail(384018, BuildConfig.TMDB_API_KEY).test().apply {
                        assertError(it.throwable)
                    }
                }
            }
        }
    }

    @Test
    fun `fetch detail tv error from api service`() {

        val responseDetailMovie = Throwable("Error Detail Tv")

        whenever(movieService.getMovieDetail(60735, BuildConfig.TMDB_API_KEY)).thenReturn(Single.error(responseDetailMovie))

        movieViewModel.getMovieDetail(MovieType.MOVIE, 60735)

        movieViewModel.state.observeOnce {
            when(it) {
                is ResultResponse.Failure -> {
                    movieService.getMovieDetail(60735, BuildConfig.TMDB_API_KEY).test().apply {
                        assertError(it.throwable)
                    }
                }
            }
        }
    }
}