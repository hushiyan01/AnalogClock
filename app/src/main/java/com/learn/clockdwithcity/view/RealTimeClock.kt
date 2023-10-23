package com.learn.clockdwithcity.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learn.clockdwithcity.ui.theme.Purple80
import com.learn.clockdwithcity.ui.theme.greenColor
import com.learn.clockdwithcity.utils.getStringInSecuredSharedPreferences
import com.learn.clockdwithcity.view.changetime.ChooseCityTime
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.min


@Composable
fun RealTimeClock(
    timeZoneId: String = "America/New_York",
    timeFormat: String = "hh:mm:ss a"
) {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Update every second
            currentTime = System.currentTimeMillis()
        }
    }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(300.dp)
            .height(150.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = timeZoneId,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = greenColor,
                fontSize = 25.sp,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = SimpleDateFormat(timeFormat, Locale.US).apply {
                    timeZone = TimeZone.getTimeZone(timeZoneId)
                }.format(Date(currentTime)),
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun AnalogClockComponent(
    key: String,
    timeFormat: String = "hh:mm:ss"
) {

    val context = LocalContext.current

    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var hour by remember { mutableFloatStateOf(0f) }
    var minute by remember { mutableFloatStateOf(0f) }
    var second by remember { mutableIntStateOf(0) }

    var timezone by remember {
        mutableStateOf(context.getStringInSecuredSharedPreferences(key))
    }


    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Update every second
            currentTime = System.currentTimeMillis()
            val time = SimpleDateFormat(timeFormat, Locale.US).apply {
                timeZone = TimeZone.getTimeZone(timezone)
            }.format(Date(currentTime)).trim()

            minute = time.split(":")[1].toFloat()
            hour = time.split(":")[0].toFloat() + minute / 60
            second = time.split(":")[2].toInt()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(Purple80),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(fraction = 0.78f)
                    .aspectRatio(1f)
                    .shadowCircular(
                        offsetX = 4.dp,
                        offsetY = 0.dp,
                        blurRadius = 10.dp,
                        color =  Color.Gray
                    )
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val diameter = min(size.width, size.height) * 0.9f
                    val radius = diameter / 2

                    repeat(4) {
                        val start = center - Offset(0f, radius)
                        val end = start + Offset(0f, radius / 40f)
                        rotate(it / 4f * 360) {
                            drawLine(
                                color = Color.White,
                                start = start,
                                end = end,
                                strokeWidth = 5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }

                    val secondRatio = second / 60f
                    val minuteRatio = minute / 60f
                    val hourRatio = hour / 12f

                    rotate(hourRatio * 360, center) {
                        drawLine(
                            color = Color.Black,
                            start = center - Offset(0f, radius * 0.4f),
                            end = center + Offset(0f, radius * 0.1f),
                            strokeWidth = 3.8.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    rotate(minuteRatio * 360, center) {
                        drawLine(
                            color = Color.Black,
                            start = center - Offset(0f, radius * 0.6f),
                            end = center + Offset(0f, radius * 0.1f),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    rotate(secondRatio * 360, center) {
                        drawLine(
                            color = Color.Red,
                            start = center - Offset(0f, radius * 0.7f),
                            end = center + Offset(0f, radius * 0.1f),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    drawCircle(
                        color = Color.Black,
                        radius = 5.dp.toPx(),
                        center = center
                    )
                }
            }
        }



        ChooseCityTime(key, onCityChangeCallBack = {
            timezone = context.getStringInSecuredSharedPreferences(key)
        })
    }

}