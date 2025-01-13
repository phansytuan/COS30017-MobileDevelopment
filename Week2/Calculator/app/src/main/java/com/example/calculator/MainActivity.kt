package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

lateinit var  btnPlus: Button
lateinit var btnMinus: Button
lateinit var btnMultiply: Button
lateinit var btnDivide: Button

lateinit var txtNum1:EditText
lateinit var txtNum2:EditText
lateinit var txtResult:TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus= findViewById(R.id.btnMinus)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide= findViewById(R.id.btnDivide)

        txtNum1=findViewById(R.id.txtNum1)
        txtNum2=findViewById(R.id.txtNum2)
        txtResult=findViewById(R.id.txtResult)

        btnPlus.setOnClickListener(this)
        btnMinus.setOnClickListener(this)
        btnMultiply.setOnClickListener(this)
        btnDivide.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnPlus -> {
                var Num1 = txtNum1.text.toString().toDouble()
                var Num2 = txtNum2.text.toString().toDouble()
                var result = Num1+Num2
                txtResult.setText(result.toString())
            }
            R.id.btnMultiply -> {
                var Num1 = txtNum1.text.toString().toDouble()
                var Num2 = txtNum2.text.toString().toDouble()
                var result = Num1*Num2
                txtResult.setText(result.toString())
            }
            R.id.btnDivide -> {
                var Num1 = txtNum1.text.toString().toDouble()
                var Num2 = txtNum2.text.toString().toDouble()
                var result = Num1/Num2
                txtResult.setText(result.toString())
            }
            R.id.btnMinus->{
                var Num1 = txtNum1.text.toString().toDouble()
                var Num2 = txtNum2.text.toString().toDouble()
                var result = Num1-Num2
                txtResult.setText(result.toString())
            }
        }
    }
}
