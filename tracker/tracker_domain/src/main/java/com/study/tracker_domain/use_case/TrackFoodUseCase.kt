package com.study.tracker_domain.use_case

import com.study.tracker_domain.model.MealType
import com.study.tracker_domain.model.TrackableFood
import com.study.tracker_domain.model.TrackedFood
import com.study.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFoodUseCase(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbsPer100g / 100f) * amount).roundToInt(),
                protein = ((food.proteinsPer100g / 100f) * amount).roundToInt(),
                fat = ((food.fatPer100g / 100f) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                calories = ((food.caloriesPer100g / 100f) * amount).roundToInt(),
                date = date
            )
        )
    }
}