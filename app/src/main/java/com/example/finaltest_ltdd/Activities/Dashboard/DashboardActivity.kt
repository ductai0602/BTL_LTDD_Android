package com.example.finaltest_ltdd.Activities.Dashboard

import android.content.Intent
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.startActivity
import com.example.finaltest_ltdd.Activities.Splash.GradientButton
import com.example.finaltest_ltdd.Activities.SearchResult.SearchResultActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
@Preview
fun MainScreen() {
    val locations = remember { mutableStateListOf<LocationModel>() }
    val viewModel = MainViewModel()
    var showLocationLoading by remember { mutableStateOf(true) }
    var from:String = ""
    var to:String = ""
    var classes:String = ""
    var adultPassenger: String = ""
    var childPassenger: String = ""
    val context = LocalContext.current

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
            item { //Phan noi dung
                Column (modifier = Modifier
                    .padding(32.dp)
                    .background(colorResource(R.color.darkPurple), shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                ) {
                    //Chuyen tu Section(dia diem) nay
                    YellowTitle("From")
                    val locationName:List<String> = locations.map{it.Name} //Chon cac dia diem
                    DropDownList(
                        items = locationName,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint = "Select Origin",
                        showLocationLoading = showLocationLoading
                    ) {
                        selectedItem -> from = selectedItem
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //Den Section(dia diem) nay
                    YellowTitle("to")
                    DropDownList(
                        items = locationName,
                        loadingIcon = painterResource(R.drawable.from_ic),
                        hint = "Select Destination",
                        showLocationLoading = showLocationLoading
                    ) {
                        selectedItem -> to = selectedItem
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //So luong khach hang(passenger count)
                    YellowTitle("Passengers")
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //So luong nguoi lon
                        PassengerCounter(
                            title = "Adult",
                            modifier = Modifier.weight(1f),
                            onItemSelected = {adultPassenger = it}
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        //So luong nguoi tre
                        PassengerCounter(
                            title = "Child",
                            modifier = Modifier.weight(1f),
                            onItemSelected = {childPassenger = it}
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

//                    Thoi gian khoi hanh
                    Row {
                        YellowTitle("Departure date", Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(16.dp))
                        YellowTitle("Return date", Modifier.weight(1f))
                    }
                    DatePickerScreen(Modifier.weight(1f))
                    Spacer(modifier = Modifier.height(16.dp))


                    //class(rank) Section
                    YellowTitle("class")
                    val classItems = listOf("Business class", "First class", "Economy class")
                    DropDownList(
                        items = classItems,
                        loadingIcon = painterResource(R.drawable.seat_black_ic),
                        hint = "Select Class",
                        showLocationLoading = showLocationLoading
                    ) {
                        selectedItem -> classes = selectedItem
                    }
                    // Search Button
                    Spacer(modifier = Modifier.height(16.dp))
                    GradientButton(
                        onClick={
                            val intent= Intent(context, SearchResultActivity::class.java).apply {
                                putExtra( "from", from)
                                putExtra( "to", to)
                                putExtra( "numPassenger",  adultPassenger+childPassenger)
                            }
                            startActivity(context, intent, null)
                        },
                        text="Search",
                    )
                }
            }
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