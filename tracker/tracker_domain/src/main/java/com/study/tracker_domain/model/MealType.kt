package com.study.tracker_domain.model

sealed class MealType(val name: String) {

   object Breakfast : MealType("breakfast")
   object Launch : MealType("launch")
   object Dinner : MealType("dinner")
   object Snack : MealType("snack")

    companion object {
        fun fromString(name: String): MealType {
            return when(name){
                 "breakfast" -> Breakfast
                 "launch" -> Launch
                 "dinner" -> Dinner
                 "snack" -> Snack
                else -> Dinner
            }
        }
    }

}