package com.example.finaltest_ltdd.Activities.Splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.finaltest_ltdd.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.finaltest_ltdd.Activities.Login.Components.DontHaveAcc
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SplashActivity(
    navController: NavHostController
){
    Column(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout() {
            val(backgroundImg, title, subtitle, signin, signup) = createRefs()
            Image(
                painter = painterResource(R.drawable.splash_bg),
                contentDescription = null,
                modifier = Modifier.constrainAs(backgroundImg){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }.fillMaxSize()
            )

            val styledText = buildAnnotatedString {
                append("Discover your\n")
                append("Dream ")
                withStyle(SpanStyle(color = colorResource(R.color.orange))) {
                    append("Flight")
                }
                append("\nEasily")
            }

            Text(
                text = styledText,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 44.sp,
                modifier = Modifier
                    .padding(top = 64.dp, start = 24.dp) // chỉ cần padding start, không cần end
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start) // <-- Ràng buộc về trái
                    }
            )

            Text(
                text = stringResource(R.string.subtitle_splash),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.orange),
                modifier = Modifier.padding(top = 32.dp, start = 16.dp).constrainAs(subtitle){
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                }
            )

            Box(modifier = Modifier.padding(bottom = 48.dp).constrainAs(signin){
                bottom.linkTo(parent.bottom)
            }){
                GradientButton(onClick = {navController.navigate("login")},
                    text = "Sign In With Email", 32)
            }

            Box(modifier = Modifier.padding(top = 16.dp).constrainAs(signup){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }){
                DontHaveAcc(onSignupTap = {
                    navController.navigate("signup")
                })
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    SplashActivity(rememberNavController())
}