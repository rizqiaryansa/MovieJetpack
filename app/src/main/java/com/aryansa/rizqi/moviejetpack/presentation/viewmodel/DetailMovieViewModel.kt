package com.aryansa.rizqi.moviejetpack.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aryansa.rizqi.moviejetpack.R
import com.aryansa.rizqi.moviejetpack.base.BaseViewModel
import com.aryansa.rizqi.moviejetpack.domain.model.local.MovieEntity
import com.aryansa.rizqi.moviejetpack.domain.model.remote.MovieDetailResponse
import com.aryansa.rizqi.moviejetpack.domain.repository.FavoriteRepository
import com.aryansa.rizqi.moviejetpack.domain.repository.MovieRepository
import com.aryansa.rizqi.moviejetpack.utils.FavoriteResponse
import com.aryansa.rizqi.moviejetpack.utils.MovieType
import com.aryansa.rizqi.moviejetpack.utils.ResultResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailMovieViewModel @Inject
    constructor(private val repository: MovieRepository,
                private val favoriteRepository: FavoriteRepository
): BaseViewModel() {

    private val _state = MutableLiveData<ResultResponse>()
    val state: LiveData<ResultResponse> = _state

    private val _stateFavorite = MutableLiveData<FavoriteResponse>()
    val stateFavorite: LiveData<FavoriteResponse> = _stateFavorite

    private val _isFavorite = MutableLiveData<Boolean>().apply { value = false }
    val isFavorite = _isFavorite

    val iconFavorite: LiveData<Int> = Transformations.map(isFavorite) {
        if(it) R.drawable.ic_favorite_orange else R.drawable.ic_favorite_border
    }

    fun getMovieDetail(typeMovie: MovieType, id: Int) {
        disposable.add(repository.fetchDetailMovies(typeMovie, id).doOnSubscribe {
            setIsLoading(true)
        }.doAfterTerminate {
            setIsLoading(false)
        }.subscribeOn(Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe({
            _state.postValue(ResultResponse.SuccessDetail(it))
        }, {
            _state.postValue(ResultResponse.Failure(it))
        }))
    }

    fun checkIsFavorite(id: Int) {
        disposable.add(favoriteRepository.getCountMovie(id).subscribeOn(Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe {
            _isFavorite.value = it > 0
        })
    }

    fun setDeleteFavorite(id: Int) {
        disposable.add(favoriteRepository.deleteFavoriteMovie(id)
            .subscribeOn(Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe ({
                _stateFavorite.postValue(FavoriteResponse.DeleteFavorite)
            }, {
                _stateFavorite.postValue(FavoriteResponse.Failure(it))
            }))
    }

    fun setSaveFavorite(movieDetailResponse: MovieDetailResponse, typeMovie: MovieType) {
        val entityMovie = MovieEntity(
            idMovie = movieDetailResponse.id,
            title = movieDetailResponse.getTitleMovie(),
            releaseDate = movieDetailResponse.getYearMovie(),
            posterPath = movieDetailResponse.posterPath
        )

        when(typeMovie) {
            MovieType.MOVIE -> entityMovie.typeMovie = 0
            else -> entityMovie.typeMovie = 1
        }

        disposable.add(favoriteRepository.saveFavoriteMovie(entityMovie)
            .subscribeOn(Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe ({
                _stateFavorite.postValue(FavoriteResponse.SaveFavorite)
            }, {
                _stateFavorite.postValue(FavoriteResponse.Failure(it))
            }))
    }
}