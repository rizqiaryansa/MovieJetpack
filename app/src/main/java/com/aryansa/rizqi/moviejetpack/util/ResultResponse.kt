package com.aryansa.rizqi.moviejetpack.util

import com.aryansa.rizqi.moviejetpack.model.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.model.MovieListResponse

sealed class ResultResponse {
    data class Success(val data: MovieListResponse?): ResultResponse()
    data class SuccessDetail(val data: MovieDetailResponse?): ResultResponse()
    data class Failure(val throwable: Throwable): ResultResponse()
}