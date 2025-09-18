package com.gramirez.games.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.gramirez.games.network.APIClient
import com.gramirez.games.network.GameService
import java.sql.SQLException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GuessViewModel : ViewModel() {

    private val guessService = APIClient.retrofit.create(GameService::class.java)

    val isLoading = mutableStateOf(false)
    val inputNumber = mutableStateOf("")
    val result = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)

    fun verify() {
        val number = inputNumber.value.toIntOrNull()

        if(number == null) {
            errorMessage.value = "Please enter a valid number (0-10)"
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val resNum = guessService.verifyAttempt(number)
                result.value = resNum
            } catch (e: SQLException) {
                errorMessage.value = e.message ?: "There was an SQL error on number verification!"
            } catch (e2: Exception) {
                errorMessage.value = e2.message ?: "something went wrong on number verification!"
            } finally {
                isLoading.value = false
            }
        }
    }
}