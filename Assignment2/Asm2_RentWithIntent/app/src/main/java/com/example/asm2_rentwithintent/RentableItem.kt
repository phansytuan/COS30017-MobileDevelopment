package com.example.asm2_rentwithintent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RentableItem(
    val name: String,
    val rating: Float,               // Star rating (0-5)
    val attributes: List<String>,    // Multi-choice attribute (e.g., color, type)
    val price: Int,                  // Price per month in credits
    val accessories: List<String>,   // List of rental accessories
    val remainingRentalTime: Int? = null,  // Remaining rental time in days (if rented)
    val imageResId: Int              // Drawable resource ID for the item image
) : Parcelable


