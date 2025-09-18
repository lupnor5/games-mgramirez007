package com.gramirez.games.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gramirez.games.network.APIClient
import com.gramirez.games.network.GameService
import kotlinx.coroutines.launch

class HoroscopeViewModel : ViewModel(){

    private val horoscopeService = APIClient.retrofit.create(GameService::class.java)

    val isLoading = mutableStateOf(false)

    val inputNumber = mutableStateOf("")

    val errorMessage = mutableStateOf<String?>(null)
    val content = mutableStateOf<String?>(null)

    fun findContent() {
        isLoading.value = true

        val number = inputNumber.value.toIntOrNull()

        if (number == null) {
            errorMessage.value = "Please enter a valid number (1, 12)"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val result = horoscopeService.getHoroscope(number)
                content.value  = result
            } catch (e : Exception) {
                errorMessage.value = e.message ?: "Something went wrong when retrieving horoscope!"
            } finally {
                isLoading.value = false
            }
        }
    }
}