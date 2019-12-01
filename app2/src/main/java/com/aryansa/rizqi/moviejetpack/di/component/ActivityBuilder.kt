package com.aryansa.rizqi.moviejetpack.di.component

import com.aryansa.rizqi.moviejetpack.di.module.FavoriteModule
import com.aryansa.rizqi.moviejetpack.di.module.FragmentModule
import com.aryansa.rizqi.moviejetpack.presentation.view.activity.DetailActivity
import com.aryansa.rizqi.moviejetpack.presentation.view.activity.FavoriteActivity
import com.aryansa.rizqi.moviejetpack.presentation.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    internal abstract fun contributeMainActivity(): MainActivity
    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    internal abstract fun contributeFavoriteActivity(): FavoriteActivity
    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity
}