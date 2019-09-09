package com.aryansa.rizqi.moviejetpack.util

sealed class FavoriteResponse {
    object SaveFavorite: FavoriteResponse()
    object DeleteFavorite: FavoriteResponse()
    data class Failure(val throwable: Throwable): FavoriteResponse()
}