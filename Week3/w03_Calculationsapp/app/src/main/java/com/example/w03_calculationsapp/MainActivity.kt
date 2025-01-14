package com.example.w03_calculationsapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        val number1 = findViewById<EditText>(R.id.number1)
        val number2 = findViewById<EditText>(R.id.number2)
        val addButton = findViewById<Button>(R.id.addButton)
        val multiplyButton = findViewById<Button>(R.id.multiplyButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        // Add button click listener
        addButton.setOnClickListener {
            val num1 = number1.text.toString().toDoubleOrNull() ?: 0.0
            val num2 = number2.text.toString().toDoubleOrNull() ?: 0.0
            val result = num1 + num2
            resultText.text = getString(R.string.result_text) + " $result"
        }

        // Multiply button click listener
        multiplyButton.setOnClickListener {
            val num1 = number1.text.toString().toDoubleOrNull() ?: 0.0
            val num2 = number2.text.toString().toDoubleOrNull() ?: 0.0
            val result = num1 * num2
            resultText.text = getString(R.string.result_text) + " $result"
        }
    }
}