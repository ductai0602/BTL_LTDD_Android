package com.example.finaltest_ltdd.Domain

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class BookingModel(
    var id: String? = null,
    val flight: FlightModel? = null,
    val paymentMethod: String? = null,
    val deliveryAddress: String? = null,
    val userId: String? = null,
    val status: String? = null
) : Serializable