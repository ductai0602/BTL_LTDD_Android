package com.example.finaltest_ltdd.Activities.Dashboard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finaltest_ltdd.Activities.Splash.StatusTopBarColor
import com.example.finaltest_ltdd.Domain.LocationModel
import com.example.finaltest_ltdd.R
import com.example.finaltest_ltdd.ViewModel.MainViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScree()
        }
    }
}

@Composable
@Preview
fun MainScree() {
    val locations = remember { mutableStateListOf<LocationModel>() }
    val viewModel = MainViewModel()
    var showLocationLoading by remember { mutableStateOf(true) }
    var from:String = ""
    var to:String = ""
    var classes:String = ""
    var adultPassenger: String = ""
    var childPassenger: String = ""

    StatusTopBarColor()

    LaunchedEffect(Unit) {
        viewModel.loadLocations().observeForever {result->
            locations.clear()
            locations.addAll(result)
            showLocationLoading = false
        }
    }

    Scaffold(bottomBar = { MyBottomBar() }, //phan hien thi menu phia duoi
        ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.darkPurple2)).padding(paddingValues = paddingValues) //dieu chinh menu
        ) {
            item { TopBar() } //phan hien thi phia tren
        }
    }
}

@Composable
fun YellowTitle(text:String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(R.color.orange),
        modifier = modifier
    )
}