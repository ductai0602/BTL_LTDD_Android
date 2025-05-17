package com.example.finaltest_ltdd.Activities.Dashboard

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.finaltest_ltdd.Activities.Dashboard.FavoriteActivity
import com.example.finaltest_ltdd.R
import androidx.compose.runtime.setValue

data class BottomMenuItem(
    val label: String,
    val icon: Painter
)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    return listOf(
        BottomMenuItem(label = "Home", icon = painterResource(R.drawable.bottom_btn1)),
        BottomMenuItem(label = "Cart", icon = painterResource(R.drawable.bottom_btn2)),
        BottomMenuItem(label = "Favorite", icon = painterResource(R.drawable.bottom_btn3)),
        BottomMenuItem(label = "Order", icon = painterResource(R.drawable.bottom_btn4)),
    )
}

@Composable
@Preview
fun MyBottomBar() {
    val bottomMenuItemList = prepareBottomMenu()
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf("Home") }

    BottomAppBar(
        backgroundColor = colorResource(R.color.darkPurple),
        elevation = 3.dp
    ) {
        bottomMenuItemList.forEach { bottomMenuItem ->
            BottomNavigationItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    when (bottomMenuItem.label) {
                        "Favorite" -> {
                            val intent = Intent(context, FavoriteActivity::class.java)
                            startActivity(context, intent, null)
                        }
                        "Cart" -> {
                            // Handle Cart click
                        }
                        else -> {
                            Toast.makeText(context, bottomMenuItem.label, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = null,
                        tint = colorResource(R.color.orange),
                        modifier = Modifier.padding(top = 8.dp).size(20.dp)
                    )
                }
            )
        }
    }
}


