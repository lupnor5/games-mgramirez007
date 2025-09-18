package com.gramirez.games.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gramirez.games.network.APIClient
import com.gramirez.games.network.GameService
import kotlinx.coroutines.launch

class LotteryViewModel : ViewModel() {
    private val lotteryService = APIClient.retrofit.create(GameService::class.java)

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val numbers = mutableListOf<Int>()

    fun generateNumbers () {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {

                val result = lotteryService.generateNumbers()
                numbers.clear()
                numbers.addAll(result)
            } catch(e: Exception) {
                errorMessage.value = e.message ?: "Something went wrong when generating numbers!"
            } finally {
                isLoading.value = false
            }
        }
    }
}