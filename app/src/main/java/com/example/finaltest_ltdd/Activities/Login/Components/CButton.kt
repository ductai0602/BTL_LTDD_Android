package com.example.finaltest_ltdd.Activities.Login.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finaltest_ltdd.R


@Composable
fun CButton(
    onClick: () -> Unit = {},
    text: String,
) {
    // make this button also resuable
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(

        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(colorResource(R.color.darkPurple2))
    ) {

        Text(
            text = text,
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight(500),
                color = Color.White
            )
        )

    }
}