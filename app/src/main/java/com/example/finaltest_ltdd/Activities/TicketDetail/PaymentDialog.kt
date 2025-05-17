package com.example.finaltest_ltdd.Activities.TicketDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.finaltest_ltdd.R

@Composable
fun PaymentDialog(
    onDismiss: () -> Unit,
    onPaymentSelected: (String) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(R.color.lightPurple),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Payment Method",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                PaymentOption(
                    text = "Cash",
                    onClick = { onPaymentSelected("Cash"); onDismiss() }
                )

                Spacer(modifier = Modifier.height(8.dp))

                PaymentOption(
                    text = "VNPay",
                    onClick = { onPaymentSelected("VNPay"); onDismiss() }
                )

                Spacer(modifier = Modifier.height(8.dp))

                PaymentOption(
                    text = "MoMo",
                    onClick = { onPaymentSelected("MoMo"); onDismiss() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    color = colorResource(R.color.darkPurple2),
                    modifier = Modifier
                        .clickable { onDismiss() }
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun PaymentOption(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.darkPurple2),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}