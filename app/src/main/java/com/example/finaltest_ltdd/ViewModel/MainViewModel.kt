package com.example.finaltest_ltdd.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finaltest_ltdd.Domain.BookingModel
import com.example.finaltest_ltdd.Domain.FlightModel
import com.example.finaltest_ltdd.Domain.LocationModel
import com.example.finaltest_ltdd.Repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel {
    private val repository = MainRepository()

    fun loadLocations(): LiveData<MutableList<LocationModel>> {
        return repository.loadLocation()
    }
    fun loadFiltered(from: String, to: String):
            LiveData<MutableList<FlightModel>> {
        return repository.loadFiltered(from, to)
    }
    fun loadBookings(): LiveData<List<BookingModel>> {
        val result = MutableLiveData<List<BookingModel>>()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("bookings").child(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val bookings = mutableListOf<BookingModel>()
                        for (data in snapshot.children) {
                            try {
                                val booking = data.getValue(BookingModel::class.java)
                                booking?.let {
                                    it.id = data.key
                                    bookings.add(it)
                                } ?: Log.w("MainViewModel", "Null booking at key: ${data.key}")
                            } catch (e: Exception) {
                                Log.e("MainViewModel", "Error deserializing booking at ${data.key}: ${e.message}")
                            }
                        }
                        result.postValue(bookings)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("MainViewModel", "Failed to load bookings: ${error.message}")
                        result.postValue(emptyList())
                    }
                })
        } else {
            Log.w("MainViewModel", "No user logged in")
            result.postValue(emptyList())
        }
        return result
    }

    fun deleteBooking(booking: BookingModel) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null && booking.id != null) {
            FirebaseDatabase.getInstance().getReference("bookings").child(userId).child(booking.id!!)
                .removeValue()
                .addOnFailureListener { e ->
                    Log.e("MainViewModel", "Failed to delete booking ${booking.id}: ${e.message}")
                }
        } else {
            Log.w("MainViewModel", "Cannot delete booking: userId=$userId, bookingId=${booking.id}")
        }
    }
}