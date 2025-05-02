package com.example.finaltest_ltdd.Activities.TicketDetail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finaltest_ltdd.Activities.Splash.StatusTopBarColor
import com.example.finaltest_ltdd.Domain.FlightModel
import com.example.finaltest_ltdd.R

class TicketDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val flight = intent.getSerializableExtra("flight") as FlightModel

        setContent(){
            StatusTopBarColor()

            TicketDetailScreen(
                flight = flight,
                onBackClick = { finish() },
                onDownloadTicketClick = {
                }
            )

        }
    }
}