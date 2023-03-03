package com.study.tracker_domain.use_case

import com.study.tracker_domain.model.TrackableFood
import com.study.tracker_domain.model.TrackedFood
import com.study.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DeleteTrackedFoodUseCase(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(trackedFood: TrackedFood) {
        repository.deleteTrackedFood(trackedFood)
    }
}