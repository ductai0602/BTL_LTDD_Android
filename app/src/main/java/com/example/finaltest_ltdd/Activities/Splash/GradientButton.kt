package com.example.finaltest_ltdd.Activities.Splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finaltest_ltdd.R

@Composable
fun GradientButton(
    onClick: () -> Unit = {},
    text: String,
    padding: Int = 0
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(R.color.purple),
                        colorResource(R.color.pink)
                    )
                ),
                shape = MaterialTheme.shapes.large
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}
