package com.aryansa.rizqi.moviejetpack.di.component

import android.app.Application
import com.aryansa.rizqi.moviejetpack.di.module.*
import com.aryansa.rizqi.moviejetpack.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    NetworkModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    AppModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)
}