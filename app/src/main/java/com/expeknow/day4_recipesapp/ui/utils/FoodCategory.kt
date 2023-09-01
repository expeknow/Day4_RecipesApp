package com.expeknow.day4_recipesapp.ui.utils

import com.expeknow.day4_recipesapp.ui.utils.FoodCategory.*

enum class FoodCategory(val foodType: String) {
    LUNCH("lunch"),
    BREAKFAST("breakfast"),
    DINNER("dinner"),
    BRUNCH("brunch"),
    ALLDAY("all day")
}

fun getAllCategory(): List<FoodCategory> {
     return listOf(FoodCategory.ALLDAY, FoodCategory.BREAKFAST, FoodCategory.BRUNCH,
         FoodCategory.LUNCH, FoodCategory.DINNER)
}

fun getFoodType(type: String): FoodCategory?{
    val map = values().associateBy(FoodCategory::foodType)
    return map[type]
}