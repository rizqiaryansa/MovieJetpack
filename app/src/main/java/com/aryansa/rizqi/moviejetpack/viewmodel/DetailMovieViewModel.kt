package com.aryansa.rizqi.moviejetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aryansa.rizqi.moviejetpack.base.BaseViewModel
import com.aryansa.rizqi.moviejetpack.repository.MovieRepository
import com.aryansa.rizqi.moviejetpack.util.MovieType
import com.aryansa.rizqi.moviejetpack.util.ResultResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailMovieViewModel @Inject
    constructor(private val repository: MovieRepository): BaseViewModel() {

    private val _state = MutableLiveData<ResultResponse>()
    val state: LiveData<ResultResponse> = _state

    fun getMovieDetail(typeMovie: MovieType, id: Int) {
        disposable.add(repository.fetchDetailMovies(typeMovie, id).doOnSubscribe {
            setIsLoading(true)
        }.subscribeOn(Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).subscribe({
            _state.postValue(ResultResponse.SuccessDetail(it))
            setIsLoading(false)
        }, {
            _state.postValue(ResultResponse.Failure(it))
            setIsLoading(false)
        }))
    }
}