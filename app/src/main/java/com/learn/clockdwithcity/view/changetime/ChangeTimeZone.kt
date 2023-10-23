package com.learn.clockdwithcity.view.changetime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.learn.clockdwithcity.utils.getStringInSecuredSharedPreferences
import com.learn.clockdwithcity.utils.putStringInSecuredSharedPreferences
import com.learn.clockdwithcity.view.Constants.LIST_OF_TIMEZONE

@Composable
fun ChangeTimeZone( ) {
    val context = LocalContext.current
    val selectedValue = remember { mutableStateOf(context.getStringInSecuredSharedPreferences("style")?:"Digital Clock")  }

    DisposableEffect(selectedValue.value) {
        onDispose {
            // This block of code will execute when the composable is removed from the composition
            context.putStringInSecuredSharedPreferences("style",selectedValue.value)
        }
    }
    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (String) -> Unit = { selectedValue.value = it }


    val items = listOf(
        "Digital Clock",
        "Analog Clock")
    Column(Modifier.padding(8.dp)) {
        Text(text = "Selected value: ${selectedValue.value}")
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = isSelectedItem(item),
                        onClick = { onChangeState(item) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedItem(item),
                    onClick = null
                )
                Text(
                    text = item,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun ChooseCityTime(key: String = "city1", onCityChangeCallBack:() -> Unit){
    val context = LocalContext.current
    val timeZone =context.getStringInSecuredSharedPreferences(key)
    val selectedTime = remember { mutableStateOf(timeZone) }
    val listOfTimezone = LIST_OF_TIMEZONE

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(100.dp)
        ,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ){
        Text(text = "Current City1 : ${selectedTime.value}")
        Spinner(
            items = listOfTimezone,
            selectedItem = selectedTime,
            onItemSelected = {
                context.putStringInSecuredSharedPreferences(key,it)
                onCityChangeCallBack()
            })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    items: List<String>,
    selectedItem: MutableState<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp))
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedItem.value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)

        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem.value = item
                        onItemSelected(item)
                        expanded = false
                    },
                    text ={Text(text = item, modifier = Modifier.padding(8.dp))}
                )
            }
        }
    }
}
