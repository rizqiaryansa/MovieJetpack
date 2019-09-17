package com.aryansa.rizqi.moviejetpack.data.repository

import com.aryansa.rizqi.moviejetpack.BuildConfig
import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieListResponse
import com.aryansa.rizqi.moviejetpack.data.source.remote.MovieService
import com.aryansa.rizqi.moviejetpack.domain.repository.MovieRepository
import com.aryansa.rizqi.moviejetpack.utils.MovieType
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject
constructor(private val apiService : MovieService): MovieRepository {

    private val apiKey = BuildConfig.TMDB_API_KEY

    override fun fetchMovies(type: MovieType): Single<MovieListResponse> {
        return when(type) {
            MovieType.MOVIE -> apiService.getMovie(apiKey)
            else -> apiService.getTvShow(apiKey)
        }
    }

    override fun fetchDetailMovies(type: MovieType, id: Int): Single<MovieDetailResponse> {
        return when(type) {
            MovieType.MOVIE -> apiService.getMovieDetail(id, apiKey)
            else -> apiService.getTvDetail(id, apiKey)
        }
    }
}