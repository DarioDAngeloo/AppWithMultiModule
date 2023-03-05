package com.study.tracker_domain.use_case

import com.google.common.truth.Truth.assertThat
import com.study.core.domain.model.ActivityLevel
import com.study.core.domain.model.Gender
import com.study.core.domain.model.GoalType
import com.study.core.domain.model.UserInfo
import com.study.core.domain.preferences.Preferences
import com.study.tracker_domain.model.MealType
import com.study.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsUseCaseTest {

    private lateinit var calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase

    @Before
    fun setup() {
        val preferences = mockk<Preferences>(relaxed = false)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 22,
            weight = 1f,
            height = 1,
            activityLevel = ActivityLevel.High,
            goalType = GoalType.GainWeight,
            carbRatio = 1f,
            proteinRatio = 1f,
            fatRatio = 1f
        )
        calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
    }


    @Test
    fun `Calories for breakfast correctly calculated`() {

        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                fat = Random.nextInt(100),
                protein = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("breakfast", "lunch", "dinner", "snack").random()
                ),
                imageUrl = null,
                amount = 1,
                date = LocalDate.now(),
                calories = Random.nextInt(2000),
            )
        }

        val result = calculateMealNutrientsUseCase(trackedFoods)

        val breakfastCalories = result.mealNutrients.values
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        val expectedCalories = trackedFoods
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }


        assertThat(breakfastCalories).isEqualTo(expectedCalories)

    }


}