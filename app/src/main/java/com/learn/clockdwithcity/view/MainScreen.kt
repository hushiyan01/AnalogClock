package com.learn.clockdwithcity.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.learn.clockdwithcity.utils.getStringInSecuredSharedPreferences
import com.learn.clockdwithcity.view.changetime.ChooseCityTime
import com.learn.clockdwithcity.view.navigation.NavRoutes.CHANGE_TIME_SCREEN

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(
    navController: NavHostController,
) {

    val context = LocalContext.current
    val timeFormat = context.getStringInSecuredSharedPreferences("format")
    val style = context.getStringInSecuredSharedPreferences("style")
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            modifier = Modifier.padding(20.dp),
            onClick = { navController.navigate(CHANGE_TIME_SCREEN) }) {
            Text(
                text = "Change setting",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        val city1 = context.getStringInSecuredSharedPreferences("city1")
        val city2 = context.getStringInSecuredSharedPreferences("city2")
        if(style =="Digital Clock"){
            RealTimeClock(city1,timeFormat = timeFormat)
            RealTimeClock(city2,timeFormat = timeFormat)
        }else{
            AnalogClockComponent("city1", timeFormat = timeFormat)
            AnalogClockComponent("city2", timeFormat = timeFormat)
        }



    }

}
