package com.study.tracker_domain.use_case

import com.study.tracker_domain.model.TrackableFood
import com.study.tracker_domain.model.TrackedFood
import com.study.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodForDateUseCase(
    private val repository: TrackerRepository,
) {
    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> {
        return repository.getFoodsForDate(date)
    }
}