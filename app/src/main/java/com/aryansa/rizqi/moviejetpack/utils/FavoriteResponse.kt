package com.aryansa.rizqi.moviejetpack.utils

sealed class FavoriteResponse {
    object SaveFavorite: FavoriteResponse()
    object DeleteFavorite: FavoriteResponse()
    data class Failure(val throwable: Throwable): FavoriteResponse()
}