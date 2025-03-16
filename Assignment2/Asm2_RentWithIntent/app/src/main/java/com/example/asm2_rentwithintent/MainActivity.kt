package com.example.asm2_rentwithintent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    // Use mutableListOf to allow dynamic updates.
    private val rentableItems = mutableListOf(
        RentableItem(
            name = "\uD83C\uDFB9 Piano",
            rating = 4.5f,
            attributes = listOf("Black", "Grand"),
            price = 50,
            accessories = listOf("Bench", "Cover"),
            remainingRentalTime = null,
            imageResId = R.drawable.bicycle
        ),
        RentableItem(
            name = "\uD83E\uDD41 Drum",
            rating = 4.0f,
            attributes = listOf("Wooden", "Acoustic"),
            price = 40,
            accessories = listOf("Drumsticks", "Stand"),
            remainingRentalTime = 3,   // Already rented for 3 days remaining
            imageResId = R.drawable.scooter
        ),
        RentableItem(
            name = "\uD83C\uDFB7 Saxophone",
            rating = 3.5f,
            attributes = listOf("Brass", "Alto"),
            price = 30,
            accessories = listOf("Mouthpiece", "Ligature"),
            remainingRentalTime = null,
            imageResId = R.drawable.skateboard
        )
    )
    private var currentIndex = 0

    private lateinit var itemImage: ImageView
    private lateinit var itemName: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var itemAttributes: TextView
    private lateinit var itemAccessories: TextView
    private lateinit var itemPrice: TextView
    private lateinit var itemRemainingTime: TextView
    private lateinit var nextButton: Button
    private lateinit var borrowButton: Button

    // Update the item based on the computed remaining rental time from DetailActivity.
    private val detailActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bookingMessage = result.data?.getStringExtra("bookingResult")
            val newRemainingTime = result.data?.getIntExtra("remainingRentalTime", -1) ?: -1
            if (newRemainingTime > 0) {
                rentableItems[currentIndex] = rentableItems[currentIndex].copy(remainingRentalTime = newRemainingTime)
            }
            displayCurrentItem()
            bookingMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the AppBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        itemImage = findViewById(R.id.itemImage)
        itemName = findViewById(R.id.itemName)
        ratingBar = findViewById(R.id.ratingBar)
        itemAttributes = findViewById(R.id.itemAttributes)
        itemAccessories = findViewById(R.id.itemAccessories)
        itemPrice = findViewById(R.id.itemPrice)
        itemRemainingTime = findViewById(R.id.itemRemainingTime)
        nextButton = findViewById(R.id.nextButton)
        borrowButton = findViewById(R.id.borrowButton)

        displayCurrentItem()

        // Update the rating interactively.
        ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                rentableItems[currentIndex] = rentableItems[currentIndex].copy(rating = rating)
                Toast.makeText(this, "Rating updated to $rating", Toast.LENGTH_SHORT).show()
            }
        }

        // Allow dynamic update of accessories by tapping on the text.
        itemAccessories.setOnClickListener { showAccessoryDialog() }

        // Allow dynamic update of remaining rental time by tapping on the text.
        itemRemainingTime.setOnClickListener { showRemainingTimeDialog() }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % rentableItems.size
            displayCurrentItem()
        }

        borrowButton.setOnClickListener {
            val currentItem = rentableItems[currentIndex]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("rentableItem", currentItem)
            detailActivityResult.launch(intent)
        }
    }

    private fun displayCurrentItem() {
        val currentItem = rentableItems[currentIndex]
        itemImage.setImageResource(currentItem.imageResId)
        itemName.text = currentItem.name
        ratingBar.rating = currentItem.rating
        itemAttributes.text = "Attributes: ${currentItem.attributes.joinToString()}"
        itemAccessories.text = "Accessories: ${currentItem.accessories.joinToString()}"
        itemPrice.text = "Price: ${currentItem.price} credits/month"
        itemRemainingTime.text = if (currentItem.remainingRentalTime != null)
            "Remaining Rental Time: ${currentItem.remainingRentalTime} months"
        else "Remaining Rental Time: N/A"
    }

    // Shows a dialog to add a new accessory.
    private fun showAccessoryDialog() {
        val editText = EditText(this).apply { hint = "Enter new accessory" }
        AlertDialog.Builder(this)
            .setTitle("Update Accessories")
            .setMessage("Add a new accessory to this item:")
            .setView(editText)
            .setPositiveButton("Add") { dialog, _ ->
                val newAccessory = editText.text.toString().trim()
                if (newAccessory.isNotEmpty()) {
                    val currentItem = rentableItems[currentIndex]
                    val updatedAccessories = currentItem.accessories.toMutableList()
                    updatedAccessories.add(newAccessory)
                    rentableItems[currentIndex] = currentItem.copy(accessories = updatedAccessories)
                    displayCurrentItem()
                    Toast.makeText(this, "Accessory added", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    // Shows a dialog to update the remaining rental time manually.
    private fun showRemainingTimeDialog() {
        val editText = EditText(this).apply {
            hint = "Enter remaining rental time (months)"
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        AlertDialog.Builder(this)
            .setTitle("Update Remaining Rental Time")
            .setMessage("Set the remaining rental time for this item:")
            .setView(editText)
            .setPositiveButton("Update") { dialog, _ ->
                val timeStr = editText.text.toString().trim()
                if (timeStr.isNotEmpty()) {
                    val newTime = timeStr.toIntOrNull()
                    if (newTime != null) {
                        val currentItem = rentableItems[currentIndex]
                        rentableItems[currentIndex] = currentItem.copy(remainingRentalTime = newTime)
                        displayCurrentItem()
                        Toast.makeText(this, "Remaining time updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
