package com.gramirez.games.navigation


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gramirez.games.screen.GuessScreen
import com.gramirez.games.screen.HoroscopeScreen
import com.gramirez.games.screen.LotteryScreen
import com.gramirez.games.screen.MainScreen
import com.gramirez.games.viewmodel.GuessViewModel
import com.gramirez.games.viewmodel.HoroscopeViewModel
import com.gramirez.games.viewmodel.LotteryViewModel
import kotlinx.coroutines.launch

@Composable
@SuppressLint("ViewModelConstructorInComposable")
fun GameNavigation() {
    val navController =  rememberNavController()

    val lotteryViewModel = LotteryViewModel()
    val guessViewModel = GuessViewModel()
    val horoscopeViewModel = HoroscopeViewModel()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope ()


    Scaffold  (
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        scaffoldState = scaffoldState,
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                TextButton(
                    onClick=  {
                        navController.navigate("lottery")
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                ) { Text ("Lottery") }

                TextButton(
                    onClick=  {
                        navController.navigate("guess")
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                ) { Text ("Guess the number") }

                TextButton(
                    onClick=  {
                        navController.navigate("horoscope")
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                ) { Text ("Read your horoscope") }
            }
        },

        topBar = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            val appBarTitle = when(currentRoute) {
                "lottery" -> "Classic Lottery"
                "guess" -> "Guess the number"
                "horoscope" -> "Read your horoscope"
                else -> "Games and more..."
            }

            TopAppBar (
                title =  {
                    Text(
                        text = appBarTitle,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold

                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (currentRoute == "main") {
                                scope.launch { scaffoldState.drawerState.open() }
                            } else {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                           imageVector = (if (currentRoute == "main") Icons.Filled.Menu else Icons.AutoMirrored.Filled.ArrowBack),
                           contentDescription = (if (currentRoute == "main") "Menu" else "Return")
                        )
                    }
                }
            )
        }

    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ){
            composable("main") {
                MainScreen(
                    navController
                )
            }
            composable("lottery") {
                LotteryScreen(
                    navController,
                    lotteryViewModel
                )
            }
            composable("guess") {
                GuessScreen(
                    navController,
                    guessViewModel
                )
            }

            composable("horoscope") {
                HoroscopeScreen(
                    navController,
                    horoscopeViewModel
                )
            }
        }

    }
}