package com.example.w03_lightmeup
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat

class MainActivity : AppCompatActivity() {

    private var isIconOneDisplayed = true // Track which icon is displayed
    private var clickCount = 0 // Count the number of clicks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView: ImageView = findViewById(R.id.imageView)
        val textView: TextView = findViewById(R.id.textView)
        val counterTextView: TextView = findViewById(R.id.counterTextView)

        // Set initial states
        imageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_assignment_late_24px))
        textView.text = getString(R.string.icon_late)
        counterTextView.text = getString(R.string.tap_count, clickCount)

        // Set click listener for the imageView
        imageView.setOnClickListener {
            // Animate and toggle icon
            animateIcon(imageView)

            // Update icon and text
            if (isIconOneDisplayed) {
                imageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_assignment_turned_in_24px))
                textView.text = getString(R.string.icon_done)
            } else {
                imageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_assignment_late_24px))
                textView.text = getString(R.string.icon_late)
            }

            isIconOneDisplayed = !isIconOneDisplayed
            clickCount++

            // Update click counter
            counterTextView.text = getString(R.string.tap_count, clickCount)
        }
    }

    private fun animateIcon(imageView: ImageView) {
        ViewCompat.animate(imageView)
            .rotationY(360f)
            .setDuration(300)
            .start()
    }
}

