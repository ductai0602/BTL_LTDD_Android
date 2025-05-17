package com.example.finaltest_ltdd.Activities.TicketDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.finaltest_ltdd.Activities.SeatSelect.TicketDetailHeader
import com.example.finaltest_ltdd.Activities.Splash.GradientButton
import com.example.finaltest_ltdd.Domain.BookingModel
import com.example.finaltest_ltdd.Domain.FlightModel
import com.example.finaltest_ltdd.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color

@Composable
fun TicketDetailScreen(
    flight: FlightModel,
    isBooked: Boolean, // Added isBooked parameter
    onBackClick: () -> Unit,
    onDownloadTicketClick: () -> Unit
) {
    val showPaymentDialog = remember { mutableStateOf(false) }
    val showAddressDialog = remember { mutableStateOf(false) }
    val showConfirmDialog = remember { mutableStateOf(false) }
    val showLoading = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val selectedPaymentMethod = remember { mutableStateOf("") }

    // Khởi tạo Firebase
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val database = FirebaseDatabase.getInstance().getReference("bookings")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.darkPurple2))
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(colorResource(R.color.darkPurple2))
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.darkPurple2))
            ) {
                val (topSection, ticketDetail) = createRefs()

                TicketDetailHeader(
                    onBackClick = onBackClick,
                    modifier = Modifier.constrainAs(topSection) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                TicketDetailContent(
                    flight = flight,
                    modifier = Modifier.constrainAs(ticketDetail) {
                        top.linkTo(parent.top, margin = 110.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }

            // Chỉ hiển thị nút Buy nếu không phải vé đã đặt
            if (!isBooked && currentUser != null) {
                GradientButton(
                    onClick = { showPaymentDialog.value = true },
                    text = "Buy"
                )
            } else if (!isBooked) {
                GradientButton(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Vui lòng đăng nhập để đặt vé!")
                        }
                    },
                    text = "Buy (Login Required)"
                )
            }
            // Nếu isBooked = true, không hiển thị nút Buy
        }

        // Loading indicator
        if (showLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Snackbar Host để hiển thị thông báo
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // Dialog xác nhận trước khi thanh toán (chỉ khi không phải vé đã đặt)
        if (!isBooked && showConfirmDialog.value) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog.value = false },
                title = { Text("Xác nhận thanh toán") },
                text = {
                    Text("Bạn chắc chắn muốn thanh toán ${flight.Price} USD bằng $selectedPaymentMethod?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showConfirmDialog.value = false
                            showLoading.value = true
                            currentUser?.let { user ->
                                when (selectedPaymentMethod.value) {
                                    "Cash" -> showAddressDialog.value = true
                                    "VNPay", "MoMo" -> {
                                        if (flight.Price <= 0 || flight.Date.isEmpty()) {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("Thông tin chuyến bay không hợp lệ!")
                                            }
                                            showLoading.value = false
                                            return@TextButton
                                        }
                                        val booking = BookingModel(
                                            flight = flight,
                                            paymentMethod = selectedPaymentMethod.value,
                                            userId = user.uid,
                                            status = "Completed"
                                        )
                                        val bookingId = database.child(user.uid).push().key ?: return@TextButton
                                        database.child(user.uid).child(bookingId).setValue(booking)
                                            .addOnSuccessListener {
                                                showLoading.value = false
                                                coroutineScope.launch {
                                                    snackbarHostState.showSnackbar("Đặt vé thành công qua ${selectedPaymentMethod.value}!")
                                                }
                                                onDownloadTicketClick()
                                            }
                                            .addOnFailureListener { exception ->
                                                showLoading.value = false
                                                coroutineScope.launch {
                                                    snackbarHostState.showSnackbar("Lỗi: ${exception.message}")
                                                }
                                            }
                                    }
                                    else -> {}
                                }
                            } ?: run {
                                showLoading.value = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Người dùng không đăng nhập!")
                                }
                            }
                        }
                    ) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog.value = false }) {
                        Text("Hủy")
                    }
                }
            )
        }

        // Dialog chọn phương thức thanh toán (chỉ khi không phải vé đã đặt)
        if (!isBooked && showPaymentDialog.value && currentUser != null) {
            PaymentDialog(
                onDismiss = { showPaymentDialog.value = false },
                onPaymentSelected = { paymentMethod ->
                    selectedPaymentMethod.value = paymentMethod
                    showPaymentDialog.value = false
                    showConfirmDialog.value = true
                }
            )
        }

        // Dialog nhập địa chỉ (chỉ khi không phải vé đã đặt)
        if (!isBooked && showAddressDialog.value && currentUser != null) {
            AddressInputDialog(
                onDismiss = { showAddressDialog.value = false },
                onConfirm = { address ->
                    currentUser?.let { user ->
                        if (flight.Price <= 0 || flight.Date.isEmpty()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Thông tin chuyến bay không hợp lệ!")
                            }
                            showLoading.value = false
                            return@AddressInputDialog
                        }
                        val booking = BookingModel(
                            flight = flight,
                            paymentMethod = "Cash",
                            deliveryAddress = address,
                            userId = user.uid,
                            status = "Completed"
                        )
                        val bookingId = database.child(user.uid).push().key ?: return@AddressInputDialog
                        showLoading.value = true
                        database.child(user.uid).child(bookingId).setValue(booking)
                            .addOnSuccessListener {
                                showLoading.value = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Đặt vé thành công! Vé sẽ được giao đến: $address")
                                }
                                onDownloadTicketClick()
                            }
                            .addOnFailureListener { exception ->
                                showLoading.value = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Lỗi: ${exception.message}")
                                }
                            }
                    } ?: run {
                        showLoading.value = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Người dùng không đăng nhập!")
                        }
                    }
                }
            )
        }
    }
}