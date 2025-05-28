package com.example.debtgo.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListItem(text: String) {
    Text(
        text = "• $text",
        modifier = Modifier.padding(
            start = 16.dp,
            top = 4.dp,
            bottom = 4.dp
        ),
        fontSize = 16.sp
    )
}