package com.example.asm2_rentwithintent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {

    private lateinit var itemImage: ImageView
    private lateinit var itemDetails: TextView
    private lateinit var confirmButton: Button
    private lateinit var cancelButton: Button
    private lateinit var creditInput: EditText

    private lateinit var rentableItem: RentableItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Set up the AppBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        itemImage = findViewById(R.id.itemImage)
        itemDetails = findViewById(R.id.itemDetails)
        confirmButton = findViewById(R.id.confirmButton)
        cancelButton = findViewById(R.id.cancelButton)
        creditInput = findViewById(R.id.creditInput)

        rentableItem = intent.getParcelableExtra("rentableItem") ?: return

        // Display item image.
        itemImage.setImageResource(rentableItem.imageResId)

        // Display full item details including dynamic fields.
        itemDetails.text = """
            Booking: ${rentableItem.name}
            Price: ${rentableItem.price} credits/month
            Rating: ${rentableItem.rating} stars
            Accessories: ${rentableItem.accessories.joinToString()}
            Remaining Rental Time: ${if (rentableItem.remainingRentalTime != null) "${rentableItem.remainingRentalTime} months" else "N/A"}
        """.trimIndent()

        confirmButton.setOnClickListener {
            val enteredCredits = creditInput.text.toString().toIntOrNull()
            if (enteredCredits == null || enteredCredits < rentableItem.price) {
                creditInput.error = "Insufficient credits or invalid input."
                return@setOnClickListener
            }
            // Compute remaining rental time using the formula.
            val computedTime = enteredCredits / rentableItem.price
            val resultIntent = Intent()
            resultIntent.putExtra("bookingResult", "You have successfully booked ${rentableItem.name} for $computedTime months")
            resultIntent.putExtra("remainingRentalTime", computedTime)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
