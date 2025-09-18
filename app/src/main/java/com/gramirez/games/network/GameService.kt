package com.gramirez.games.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GameService {
    @GET("/loteria")
    suspend fun generateNumbers() : List<Int>
    @GET("/adivina")
    suspend fun verifyAttempt(@Query("intento") attempt: Int): String

    @GET("/horoscopo")
    suspend fun getHoroscope(@Query("signo") sign: Int): String
}