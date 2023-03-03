package com.study.tracker_presentation.tracker_overview

import com.study.tracker_domain.model.TrackedFood

sealed class TrackerOverViewEvent {
    object OnNextDayClick : TrackerOverViewEvent()
    object OnPreviousDayClick : TrackerOverViewEvent()
    data class OnToggleMealClick(val meal: Meal) : TrackerOverViewEvent()
    data class OnDeleteTrackedFoodClick(val trackFood: TrackedFood): TrackerOverViewEvent()
    data class OnAddFoodClick(val meal: Meal): TrackerOverViewEvent()
}