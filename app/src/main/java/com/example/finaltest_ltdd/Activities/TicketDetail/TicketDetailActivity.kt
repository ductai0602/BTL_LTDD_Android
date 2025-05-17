package com.example.finaltest_ltdd.Activities.TicketDetail

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.finaltest_ltdd.Domain.FlightModel
import com.example.finaltest_ltdd.R

class TicketDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val flight = intent.getSerializableExtra("flight") as? FlightModel
        val isBooked = intent.getBooleanExtra("isBooked", false)

        Log.d("TicketDetailActivity", "Received flight: $flight, isBooked: $isBooked")

        if (flight == null) {
            Log.e("TicketDetailActivity", "No flight data received")
            finish()
            return
        }

        setContent {
            TicketDetailScreen(
                flight = flight,
                isBooked = isBooked, // Pass isBooked to TicketDetailScreen
                onBackClick = { finish() },
                onDownloadTicketClick = { /* Handle ticket download if needed */ }
            )
        }
    }
}