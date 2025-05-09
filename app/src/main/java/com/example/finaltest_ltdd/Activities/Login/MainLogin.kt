package com.example.finaltest_ltdd.Activities.Login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finaltest_ltdd.Activities.Dashboard.DashboardActivity
import com.example.finaltest_ltdd.Activities.Splash.SplashActivity
import com.example.finaltest_ltdd.ui.theme.FinalTest_LTDDTheme
import com.google.firebase.FirebaseApp

class MainLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            FinalTest_LTDDTheme {
                NavigationView()
            }
        }
    }
}

@Composable
fun NavigationView() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome" ){
        // also pass navController to each screen so we can use navController in there
        composable("welcome"){ SplashActivity(navController)}
        composable("login"){ LoginScreen(navController)}
        composable("signup"){ SignupScreen(navController)}
        composable("new"){ DashboardActivity(navController)}
    }

}