package com.learn.clockdwithcity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier

import com.learn.clockdwithcity.ui.theme.ClockdWithCityTheme
import com.learn.clockdwithcity.view.navigation.AppNavigation




class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClockdWithCityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation()
                   // DisplayTextClock()
                   // RealTimeClock()
                }
            }
        }
    }
}

