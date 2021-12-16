package io.github.kabirnayeem99.awesomeCatApp.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.github.kabirnayeem99.awesomeCatApp.domain.repositories.CatRepository
import io.github.kabirnayeem99.awesomeCatApp.domain.useCases.GetCatFactUseCase

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideGetCatFactUseCase(repo: CatRepository): GetCatFactUseCase {
        return GetCatFactUseCase(repo)
    }
}