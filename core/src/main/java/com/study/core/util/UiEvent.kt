package com.study.core.util

sealed class UiEvent {
    data class Navigate(val router :String): UiEvent()
    object NavigateUp: UiEvent()
}