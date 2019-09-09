package com.aryansa.rizqi.moviejetpack.di.module

import com.aryansa.rizqi.moviejetpack.view.fragment.FavoriteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FavoriteModule {
    @ContributesAndroidInjector
    internal abstract fun contributeFavoriteFragment(): FavoriteFragment
}