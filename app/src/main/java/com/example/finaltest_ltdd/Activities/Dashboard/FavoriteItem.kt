package com.example.finaltest_ltdd.Activities.Dashboard

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.finaltest_ltdd.Activities.TicketDetail.TicketDetailActivity
import com.example.finaltest_ltdd.Domain.BookingModel
import com.example.finaltest_ltdd.R
import com.example.finaltest_ltdd.ViewModel.MainViewModel

@Composable
fun FavoriteItem(
    booking: BookingModel,
    index: Int,
    onDelete: () -> Unit,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val flight = booking.flight ?: return

    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                Log.d("FavoriteItem", "Launching TicketDetailActivity with flight: $flight")
                val intent = Intent(context, TicketDetailActivity::class.java).apply {
                    putExtra("flight", flight)
                    putExtra("isBooked", true) // Indicate this is a booked ticket
                }
                context.startActivity(intent)
            }
            .background(
                color = colorResource(R.color.lightPurple),
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        val (logo, timeTxt, airplaneIcon, dashLine, priceTxt, seatIcon, classTxt, fromTxt, fromShortTxt, toTxt,
            toShortTxt, deleteIcon) = createRefs()

        AsyncImage(
            model = flight.AirlineLogo ?: "https://via.placeholder.com/150",
            contentDescription = null,
            modifier = Modifier
                .size(200.dp, 50.dp)
                .constrainAs(logo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = flight.ArriveTime ?: "N/A",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = colorResource(R.color.darkPurple2),
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(timeTxt) {
                    start.linkTo(parent.start)
                    top.linkTo(logo.bottom)
                    end.linkTo(parent.end)
                }
        )

        Image(
            painter = painterResource(R.drawable.line_airple_blue),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(airplaneIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(timeTxt.bottom)
                    end.linkTo(parent.end)
                }
        )

        Image(
            painter = painterResource(R.drawable.dash_line),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(dashLine) {
                    start.linkTo(parent.start)
                    top.linkTo(airplaneIcon.bottom)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = "$${String.format("%.2f", flight.Price ?: 0.0)}",
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
            color = colorResource(R.color.orange),
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(priceTxt) {
                    top.linkTo(dashLine.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )

        Image(
            painter = painterResource(R.drawable.seat_black_ic),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(seatIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(dashLine.bottom)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = flight.ClassSeat ?: "N/A",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = colorResource(R.color.darkPurple2),
            modifier = Modifier
                .constrainAs(classTxt) {
                    start.linkTo(seatIcon.end)
                    top.linkTo(seatIcon.top)
                    bottom.linkTo(seatIcon.bottom)
                }
        )

        Text(
            text = flight.From ?: "N/A",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 16.dp)
                .constrainAs(fromTxt) {
                    top.linkTo(timeTxt.bottom)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = flight.FromShort ?: "N/A",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 16.dp)
                .constrainAs(fromShortTxt) {
                    top.linkTo(fromTxt.bottom)
                    start.linkTo(fromTxt.start)
                    end.linkTo(fromTxt.end)
                }
        )

        Text(
            text = flight.To ?: "N/A",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(end = 16.dp)
                .constrainAs(toTxt) {
                    top.linkTo(timeTxt.bottom)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = flight.ToShort ?: "N/A",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(toShortTxt) {
                    top.linkTo(toTxt.bottom)
                    start.linkTo(toTxt.start)
                    end.linkTo(toTxt.end)
                }
        )

        Image(
            painter = painterResource(R.drawable.from_ic),
            contentDescription = "Delete booking",
            modifier = Modifier
                .size(24.dp)
                .padding(8.dp)
                .clickable { onDelete() }
                .constrainAs(deleteIcon) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )
    }
}