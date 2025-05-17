package com.example.finaltest_ltdd.Activities.Dashboard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.finaltest_ltdd.Activities.Dashboard.FavoriteScreen
import com.example.finaltest_ltdd.ViewModel.MainViewModel

class FavoriteActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FavoriteScreen(
                viewModel = viewModel,
                onBackClick = { finish() }
            )
        }
    }
}