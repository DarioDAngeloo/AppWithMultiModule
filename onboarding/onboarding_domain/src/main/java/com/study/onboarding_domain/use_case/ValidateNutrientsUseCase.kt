package com.study.onboarding_domain.use_case

import com.study.core.util.UiText
import com.study.onboarding_domain.R

class ValidateNutrientsUseCase {

    operator fun invoke(
        carbsRatioText: String,
        proteinRatioText: String,
        fatRatioText: String,
    ): Result {
        val carbsRatio = carbsRatioText.toIntOrNull()
        val proteinsRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()
        if (carbsRatio == null || proteinsRatio == null || fatRatio == null) {
            return Result.Error(message = UiText.StringResource(com.study.core.R.string.error_invalid_values))
        }
        if (carbsRatio + proteinsRatio + fatRatio != 100) {
            return Result.Error(message = UiText.StringResource(com.study.core.R.string.error_not_100_percent))
        }
        return Result.Success(
            carbsRatio / 100f,
            proteinsRatio / 100f,
            fatRatio / 100f
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinsRatio: Float,
            val fatRatio: Float,
        ) : Result()

        data class Error(val message: UiText) : Result()
    }

}