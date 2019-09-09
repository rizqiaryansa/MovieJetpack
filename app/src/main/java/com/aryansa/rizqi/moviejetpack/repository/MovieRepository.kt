package com.aryansa.rizqi.moviejetpack.repository

import com.aryansa.rizqi.moviejetpack.BuildConfig
import com.aryansa.rizqi.moviejetpack.model.response.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.model.response.MovieListResponse
import com.aryansa.rizqi.moviejetpack.service.MovieService
import com.aryansa.rizqi.moviejetpack.util.MovieType
import io.reactivex.Single
import javax.inject.Inject

interface MovieRepository {
    fun fetchMovies(type: MovieType): Single<MovieListResponse>
    fun fetchDetailMovies(type: MovieType, id: Int): Single<MovieDetailResponse>
}

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