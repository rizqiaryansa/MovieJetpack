package com.aryansa.rizqi.moviejetpack.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryansa.rizqi.moviejetpack.di.component.ViewModelKey
import com.aryansa.rizqi.moviejetpack.factory.ViewModelFactory
import com.aryansa.rizqi.moviejetpack.viewmodel.DetailMovieViewModel
import com.aryansa.rizqi.moviejetpack.viewmodel.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun bindMovieViewModels(movieViewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailMovieViewModel::class)
    internal abstract fun bindDetailMovieViewModels(detailMovieViewModel: DetailMovieViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}