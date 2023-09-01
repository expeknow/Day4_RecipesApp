package com.expeknow.day4_recipesapp.ui.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class FoodListManager {

    var _currentSelectedFoodType : MutableState<FoodCategory?> = mutableStateOf(FoodCategory.ALLDAY)
    val selectedCategory : MutableState<FoodCategory?>
        @Composable get() = remember {
            _currentSelectedFoodType
        }

    fun onSelectFoodTypeChange(type: String){
        val newType = getFoodType(type)
        _currentSelectedFoodType.value = newType
        Log.d("change", _currentSelectedFoodType.value!!.foodType)
    }

    fun getFoodListByType(foodType: FoodCategory): List<FoodRowItem>{
        val foodList = SampleFoodLists()
        return when(foodType) {
            FoodCategory.ALLDAY -> foodList.FoodItemsListForAllDay
            FoodCategory.BREAKFAST -> foodList.FoodItemsListForBreakfast
            FoodCategory.BRUNCH -> foodList.FoodItemsListForBrunch
            FoodCategory.LUNCH -> foodList.FoodItemsListForLunch
            else -> foodList.FoodItemsListForDinner
        }
    }
}

