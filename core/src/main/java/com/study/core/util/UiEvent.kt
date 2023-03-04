package com.study.core.util

sealed class UiEvent {
    object Success: UiEvent()
    object PopBackStack: UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()
}