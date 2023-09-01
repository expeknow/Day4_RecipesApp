package com.expeknow.day4_recipesapp.ui.windows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expeknow.day4_recipesapp.R
import com.expeknow.day4_recipesapp.ui.theme.bold
import com.expeknow.day4_recipesapp.ui.theme.semi
import com.expeknow.day4_recipesapp.ui.utils.DefaultText
import com.expeknow.day4_recipesapp.ui.utils.FoodListManager
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.StepSize

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeWindow() {

    val modifier = Modifier.padding(horizontal = 10.dp)
    val foodListManager = remember {
        mutableStateOf(FoodListManager())
    }
    val foodCategory = foodListManager.value.selectedCategory
    val foodList = foodListManager.value.getFoodListByType(foodCategory.value!!)

    BackdropScaffold(
        peekHeight = 180.dp,
        headerHeight = 100.dp,
        backLayerBackgroundColor = Color.White,
        frontLayerScrimColor = Color.Transparent,
        backLayerContent = {

            Column {
                TopBar(modifier)

                HeaderText("Indonesian food", modifier = modifier)

                PopularFoodCard(23, "Kantang Goreng lkan Berlada", 2.5f, 4343,
                    modifier = modifier)
            }

        },
        appBar = {},
        frontLayerContent = {

            Column(modifier.verticalScroll(rememberScrollState())) {

                FilterChips(foodListManager.value)

                Column() {
                    for(food in foodList){
                        FoodListItem(
                            imageId = food.imageId,
                            dishName = food.dishName,
                            recipeType = food.dishType,
                            rating = food.rating,
                            reviews = food.reviews,
                            enoughForMin = food.enoughForMin,
                            enoughForMax = food.enoughForMax,
                            expectedTime = food.estimatedTimeInMinutes,
                            modifier = modifier
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(foodListManager: FoodListManager) {

    val tabsItem = listOf(
        "all day",
        "breakfast",
        "brunch",
        "lunch",
        "dinner"
    )

    LazyRow(Modifier.padding(horizontal = 0.dp)) {
        items(tabsItem.size){
            val tabName = tabsItem[it]
            val state = remember { mutableStateOf(false) }
            FilterChip(
                selected = state.value,
                onClick = {
                    state.value = !state.value
                    foodListManager.onSelectFoodTypeChange(tabName)
                          },
                shape = RoundedCornerShape(10.dp),
                label = { Text(text = tabName, fontWeight = FontWeight.Bold, fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 10.dp))},
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = colorResource(id = R.color.primary_green),
                    selectedLabelColor = Color.White,
                    containerColor = colorResource(id = R.color.primary_green_light),
                    labelColor = colorResource(id = R.color.primary_green),
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderWidth = 0.dp,
                    borderColor = Color.Transparent
                )
            )
        }
    }

}


@Composable
fun FoodListItem(imageId: Int, dishName: String, recipeType: String, rating: Float,
    reviews: Int, enoughForMin : Int, enoughForMax: Int, expectedTime: Int, modifier : Modifier = Modifier){

    Card(modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            ) {
                Image(painter = painterResource(id = imageId), contentDescription = "",
                    contentScale = ContentScale.Crop)
            }

            Spacer(modifier = Modifier.width(20.dp))
            Column(verticalArrangement = Arrangement.Center) {
                DefaultText(text = "$recipeType recipe".uppercase(), size = 10, colorId = R.color.primary_green,
                weight = FontWeight.SemiBold)
                DefaultText(text = dishName, size = 18, weight = FontWeight.Bold, colorId = R.color.black)
                Spacer(modifier = modifier.height(6.dp))
                RatingsAndReviewsRow(rating, reviews, numStars = 1)
                Spacer(modifier = modifier.height(10.dp))
                Row(Modifier.height(IntrinsicSize.Min)){
                    DefaultText(text = "${enoughForMin}-${enoughForMax} person", size = 12,
                        colorId = R.color.text_color_gray)

                    Spacer(modifier = Modifier.width(6.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = colorResource(id = R.color.primary_green))
                    Spacer(modifier = Modifier.width(6.dp))

                    DefaultText(text = "${expectedTime} minutes", size = 12,
                        colorId = R.color.text_color_gray)
                }
            }

        }
    }


}

@Composable
fun PopularFoodCard(ImageId: Int, dishName: String, rating: Float, reviews: Int,
        modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Card(shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(30.dp),
        ) {
            Image(painter = painterResource(id = R.drawable.dish), contentDescription = "",
            contentScale = ContentScale.Crop, modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }
        Spacer(modifier = modifier.height(20.dp))
        DefaultText(text = "Popular Today".uppercase(), size = 13, fontFamily = semi,
            colorId = R.color.primary_green)
        DefaultText(text = dishName, size = 20, weight = FontWeight.Bold, colorId = R.color.black)
        Spacer(modifier = modifier.height(10.dp))
        RatingsAndReviewsRow(rating, reviews, numStars = 5)


    }
}

@Composable
fun RatingsAndReviewsRow(rating: Float, reviews: Int, numStars : Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RatingBar(value = rating, onValueChange = {}, onRatingChanged = {},
        config = RatingBarConfig().size(15.dp).padding(1.dp).numStars(numStars).stepSize(StepSize.HALF)
        )
        Spacer(modifier = Modifier.width(10.dp))
        DefaultText(text = rating.toString(), size = 14, weight = FontWeight.SemiBold)
        DefaultText(text = "($reviews review)", size = 14)

    }
}

@Composable
fun HeaderText(foodName: String, modifier: Modifier = Modifier) {

    Column(modifier = Modifier.padding(20.dp)) {
        Row() {
            DefaultText(text = "What ", size = 25, weight = FontWeight.Bold, fontFamily = bold)
            Text(text = foodName, fontSize = 25.sp, textDecoration = TextDecoration.Underline,
                color = colorResource(id = R.color.primary_green), fontWeight = FontWeight.Bold,
            fontFamily = bold)
        }
        DefaultText(text = "Are you looking for?", size = 25, weight = FontWeight.Bold,
            fontFamily = bold)
    }

}


@Composable
fun TopBar(modifier: Modifier = Modifier) {
    
    val expanded = remember { mutableStateOf(false) }
    Row(modifier = modifier.padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ), shape = RoundedCornerShape(30.dp),
            contentPadding = PaddingValues(10.dp),
            elevation = ButtonDefaults.buttonElevation(4.dp),
        ) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu",
            tint = Color.Black)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { expanded.value = !expanded.value },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ), shape = RoundedCornerShape(30.dp),
            contentPadding = PaddingValues(10.dp),
            elevation = ButtonDefaults.buttonElevation(4.dp)) {
                Row(Modifier.height(IntrinsicSize.Min)) {
                    Image(painter = painterResource(id = R.drawable.flag),
                        contentDescription = "", Modifier.size(25.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "",
                        tint = Color.Black)
                }
        }
         
//        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
//            DropdownMenuItem(text = { Text(text = "India") }, onClick = {  })
//            DropdownMenuItem(text = { Text(text = "India") }, onClick = {  })
//            DropdownMenuItem(text = { Text(text = "India") }, onClick = {  })
//            DropdownMenuItem(text = { Text(text = "India") }, onClick = {  })
//            DropdownMenuItem(text = { Text(text = "India") }, onClick = {  })
//        }

    }
}
@Preview(showBackground = true)
@Composable
fun Preview() {
    HomeWindow()
}
