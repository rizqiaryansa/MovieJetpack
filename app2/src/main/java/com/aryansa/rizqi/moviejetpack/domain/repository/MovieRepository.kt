package com.aryansa.rizqi.moviejetpack.domain.repository

import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieListResponse
import com.aryansa.rizqi.moviejetpack.utils.MovieType
import io.reactivex.Single

interface MovieRepository {
    fun fetchMovies(type: MovieType): Single<MovieListResponse>
    fun fetchDetailMovies(type: MovieType, id: Int): Single<MovieDetailResponse>
}