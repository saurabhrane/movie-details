package me.saurabhrane.mvp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import me.saurabhrane.mvp.ui.moviedetails.CastAdapter
import me.saurabhrane.mvp.ui.moviedetails.CrewAdapter
import me.saurabhrane.mvp.ui.moviedetails.SimilarMovieAdapter

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @MovieScope
    @Provides
    fun provideCastAdapter() = CastAdapter()

    @MovieScope
    @Provides
    fun provideCrewAdapter() = CrewAdapter()

    @MovieScope
    @Provides
    fun provideSimilarMoviesAdapter() = SimilarMovieAdapter()

}