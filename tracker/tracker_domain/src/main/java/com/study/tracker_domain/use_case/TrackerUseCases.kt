package com.study.tracker_domain.use_case

data class TrackerUseCases(
    val trackFoodUseCase: TrackFoodUseCase,
    val searchFoodUseCase: SearchFoodUseCase,
    val getFoodsForDate: GetFoodForDateUseCase,
    val deleteTrackedFoodUseCase: DeleteTrackedFoodUseCase,
    val calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase
)
