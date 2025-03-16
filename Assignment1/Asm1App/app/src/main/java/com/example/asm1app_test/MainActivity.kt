package com.example.asm1app_test

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val KEY_SCORE = "score"
        private const val KEY_HOLD_COUNT = "holdCount"
        private const val KEY_HAS_FALLEN = "hasFallen"
    }

    // game state variables
    private var score = 0
    private var holdCount = 0  // tracks the current hold number (1-9)
    private var hasFallen = false

    // ui components
    private lateinit var scoreTextView: TextView
    private lateinit var climbButton: Button
    private lateinit var fallButton: Button
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity created")
        setContentView(R.layout.activity_main)

        // initialize UI components
        scoreTextView = findViewById(R.id.scoreTextView)
        climbButton = findViewById(R.id.climbButton)
        fallButton = findViewById(R.id.fallButton)
        resetButton = findViewById(R.id.resetButton)

        // restore saved state if available
        savedInstanceState?.let {
            score = it.getInt(KEY_SCORE, 0)
            holdCount = it.getInt(KEY_HOLD_COUNT, 0)
            hasFallen = it.getBoolean(KEY_HAS_FALLEN, false)
            Log.d(TAG, "onCreate: Restored state score=$score, holdCount=$holdCount, hasFallen=$hasFallen")
        }
        updateScoreDisplay()

        // climb button logic
        climbButton.setOnClickListener {
            if (hasFallen) {
                Log.d(TAG, "Climb clicked: Climber has fallen. No further climbing allowed.")
                return@setOnClickListener
            }
            if (holdCount >= 9) {
                Log.d(TAG, "Climb clicked: Top hold reached. Cannot climb further.")
                return@setOnClickListener
            }
            holdCount++
            val pointsToAdd = getPointsForHold(holdCount)
            score += pointsToAdd
            if (score > 18) score = 18
            Log.d(TAG, "Climb clicked: holdCount=$holdCount, points added=$pointsToAdd, new score=$score")
            updateScoreDisplay()
        }

        // fall button logic
        fallButton.setOnClickListener {
            if (holdCount < 1) {
                Log.d(TAG, "Fall clicked: Cannot fall before reaching hold 1.")
                return@setOnClickListener  // chi dung su kien click, tranh loi thoat nham khoi onCreate()
            }
            if (holdCount >= 9) {
                Log.d(TAG, "Fall clicked: At top hold, falling is disabled.")
                return@setOnClickListener
            }
            if (hasFallen) {
                Log.d(TAG, "Fall clicked: Climber has already fallen.")
                return@setOnClickListener
            }
            // subtract 3 points & ensuring score does not go below 0
            score = (score - 3).coerceAtLeast(0)
            hasFallen = true
            Log.d(TAG, "Fall clicked: Climber falls, new score=$score")
            updateScoreDisplay()
        }

        // reset button logic
        resetButton.setOnClickListener {
            score = 0
            holdCount = 0
            hasFallen = false
            Log.d(TAG, "Reset clicked: Game state reset.")
            updateScoreDisplay()
        }
    }

//  returns the points associated with a given hold number.
    private fun getPointsForHold(hold: Int): Int {
        return when (hold) {
            in 1..3 -> 1  // Blue zone
            in 4..6 -> 2  // Green zone
            in 7..9 -> 3  // Red zone
            else -> 0
        }
    }

    private fun updateScoreDisplay() {
        scoreTextView.text = score.toString()
        val colorResId = when (holdCount) {
            in 1..3 -> R.color.blue_zone   // Blue zone
            in 4..6 -> R.color.green_zone  // Green zone
            in 7..9 -> R.color.red_zone    // Red zone
            else -> android.R.color.black
        }
        scoreTextView.setTextColor(ContextCompat.getColor(this, colorResId))
    }

//  save game state so that it can be restored on configuration changes such as screen rotation.
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_SCORE, score)
        outState.putInt(KEY_HOLD_COUNT, holdCount)
        outState.putBoolean(KEY_HAS_FALLEN, hasFallen)
        Log.d(TAG, "onSaveInstanceState: Saving state (score=$score, holdCount=$holdCount, hasFallen=$hasFallen)")
        super.onSaveInstanceState(outState)
    }
}
