package com.example.testui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.Year

lateinit var  btnOk:Button
lateinit var btnCancel:Button
lateinit var txtName:EditText

class Car(var brand:String, var model:String, var year: Int){
    fun GetBrand() =brand
}

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

        btnOk = findViewById(R.id.btnOK)
        btnCancel= findViewById(R.id.btnCancel)
        txtName = findViewById(R.id.txtName)
        btnOk.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
    }

    fun test(a:Int, b:Int)=a+b
    fun test1(a:Int, b: Int):Int{
        return a+b;
    }

    //    Import/ Implement members
//    Tạo ra được hàm Onclick
//    r là reference/root
    override fun onClick(p0: View?) {
        // TODO("Not yet implemented")
        var text = txtName.text
        when(p0?.id){
            R.id.btnOK -> {
//                txtName.setText("Hello $text")
//                var Cars= arrayOf("Volvo", "Huyndai", "Toyota")
//                Cars[1] = "Kia"
//                Cars.set(2,"Merc")
//                txtName.setText(Cars.joinToString())

//                txtName.setText(test(5,2).toString())
//                txtName.setText(test1(5,2).toString())
                val c1=Car("Ford", "Mustang", 1969)
                txtName.setText(c1.GetBrand())

            }
            R.id.btnCancel->{
//                txtName.setText("You click to Cancel button")
                var Cars= arrayOf("Volvo", "Huyndai", "Toyota")
                var s =""
//                checkpoint@ for (i in Cars.indices){
//                    if (Cars.get(i) == "Huyndai") break@checkpoint
//                    s+=Cars.get(i)
//                }
                checkpoint@ for (i in Cars.indices){
                    if (Cars.get(i) == "Huyndai") continue@checkpoint
                    s+=Cars.get(i) + " "
                }
                txtName.setText(s)
            }
        }
    }
}
