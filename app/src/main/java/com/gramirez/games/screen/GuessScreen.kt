package com.gramirez.games.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gramirez.games.viewmodel.GuessViewModel

@Preview(showBackground = true)
@Composable
fun GuessScreen(
    navController: NavController = rememberNavController(),
    viewModel: GuessViewModel = GuessViewModel()
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
       OutlinedTextField(
           value = viewModel.inputNumber.value,
           onValueChange = {
               viewModel.inputNumber.value = it
           },
           label = {
               Text(text = "Give me a number!")
           },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
       )

       viewModel.errorMessage.value?.let {
           Text(text = it, color = Color.Red)
       }

        Button(
            onClick = viewModel::verify,
            modifier = Modifier.padding(15.dp)
        ){
            Text(text = "Try!")
        }

        if (viewModel.isLoading.value) {
            CircularProgressIndicator()
        }

        viewModel.result.value?.let{
            Text(text = it,
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }



}
