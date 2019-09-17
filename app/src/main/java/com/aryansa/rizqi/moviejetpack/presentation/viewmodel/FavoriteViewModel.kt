package com.aryansa.rizqi.moviejetpack.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aryansa.rizqi.moviejetpack.base.BaseViewModel
import com.aryansa.rizqi.moviejetpack.domain.repository.FavoriteRepository
import com.aryansa.rizqi.moviejetpack.utils.MovieType
import com.aryansa.rizqi.moviejetpack.utils.ResultResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteViewModel @Inject
    constructor(private val repository: FavoriteRepository): BaseViewModel() {

    private val _state = MutableLiveData<ResultResponse>()
    val state: LiveData<ResultResponse> = _state

    fun getFavoriteMovie(typeMovie: MovieType) {
        disposable.add(repository.getFavoriteMovie(typeMovie).subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe({
            _state.postValue(ResultResponse.SuccessFavorite(it))
        }, {
            _state.postValue(ResultResponse.Failure(it))
        }))
    }


}