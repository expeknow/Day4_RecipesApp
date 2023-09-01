package com.expeknow.day4_recipesapp.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.expeknow.day4_recipesapp.R

@Composable
fun DefaultText (text: String, size: Int, modifier : Modifier = Modifier,
                 weight: FontWeight = FontWeight.Normal,
                 colorId : Int = R.color.black, fontFamily : FontFamily = FontFamily.Default){

    Text(text = text, fontSize = size.sp, fontWeight = weight, color = colorResource(id = colorId),
    fontFamily = fontFamily, modifier = modifier)
}