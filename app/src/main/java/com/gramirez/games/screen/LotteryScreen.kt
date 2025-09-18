package com.gramirez.games.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gramirez.games.viewmodel.LotteryViewModel

@Preview(showBackground = true)
@Composable
fun LotteryScreen(
    navController: NavController = rememberNavController(),
    viewModel: LotteryViewModel = LotteryViewModel()
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.isLoading.value) {
            CircularProgressIndicator()
        } else {
          Button(
              onClick = viewModel::generateNumbers
          ) {
              Text("Generate numbers!")
          }

          viewModel.errorMessage.value?.let{
              Text(text = it, color = Color.Red)
          }

          if (viewModel.numbers.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(top = 15.dp)
            ) {
                itemsIndexed(viewModel.numbers) {
                    index, item ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = Color.Red,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = item.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Black
                        )
                    }

                }
            }
          }

        }
    }
}