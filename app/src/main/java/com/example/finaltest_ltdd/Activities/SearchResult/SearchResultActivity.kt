package com.example.finaltest_ltdd.Activities.SearchResult

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.finaltest_ltdd.Activities.Splash.StatusTopBarColor
import com.example.finaltest_ltdd.ViewModel.MainViewModel

class SearchResultActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()
    private var from: String = ""
    private var to: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        from = intent.getStringExtra( "from") ?: ""
        to = intent.getStringExtra( "to") ?: ""

       setContent(){
           StatusTopBarColor()
           ItemListScreen(
               from = from,
               to = to,
               viewModel = viewModel,
               onBackClick = {finish()}
           )
       }
    }

}