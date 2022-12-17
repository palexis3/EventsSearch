package com.example.eventsearch.data.repository

import com.example.eventsearch.data.repository.search.SearchRepository
import com.example.eventsearch.data.repository.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}
