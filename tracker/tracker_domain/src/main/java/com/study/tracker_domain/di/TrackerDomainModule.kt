package com.study.tracker_domain.di

import com.study.core.domain.preferences.Preferences
import com.study.tracker_domain.repository.TrackerRepository
import com.study.tracker_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun providesTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences,
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFoodUseCase = TrackFoodUseCase(repository),
            searchFoodUseCase = SearchFoodUseCase(repository),
            getFoodsForDate = GetFoodForDateUseCase(repository),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(repository),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
        )
    }

}