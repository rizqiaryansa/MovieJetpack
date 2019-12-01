package com.aryansa.rizqi.moviejetpack.di.module

import com.aryansa.rizqi.moviejetpack.data.source.local.dao.MovieDao
import com.aryansa.rizqi.moviejetpack.data.repository.FavoriteRepositoryImpl
import com.aryansa.rizqi.moviejetpack.data.repository.MovieRepositoryImpl
import com.aryansa.rizqi.moviejetpack.data.source.remote.MovieService
import com.aryansa.rizqi.moviejetpack.domain.repository.FavoriteRepository
import com.aryansa.rizqi.moviejetpack.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(apiService: MovieService): MovieRepository = MovieRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideFavoriteRepository(dao: MovieDao): FavoriteRepository = FavoriteRepositoryImpl(dao)
}