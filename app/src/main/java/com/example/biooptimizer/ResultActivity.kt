package com.example.optimizer

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.biooptimizer.R
import java.text.DecimalFormat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        // Elements
        val yieldText = findViewById<TextView>(R.id.textyield)
        val resultText = findViewById<TextView>(R.id.textresult)
        val gluText = findViewById<TextView>(R.id.textglu)
        val timeText = findViewById<TextView>(R.id.texttime)
        val mgText = findViewById<TextView>(R.id.textmg)
        val naText = findViewById<TextView>(R.id.textna)
        val optHeading = findViewById<TextView>(R.id.header)


        var yield = intent.getStringExtra("yield")?: "45"
        var resultValue = intent.getStringExtra("resultValue")?: "0"
        var time = intent.getStringExtra("time")?: "28"
        var glu  = intent.getStringExtra("glu")?: "0.8"
        var mg = intent.getStringExtra("mg")?: "0.018"
        var na = intent.getStringExtra("na")?: "1.14"
        val receivedString = intent.getStringExtra("resultValue")

        val optimizerType = intent.getStringExtra("optimizer")


        if (receivedString != null) {
            when(optimizerType){

                "time" -> resultText.text = "Protein per unit time: $resultValue"
                "cost" -> resultText.text = "Cost per unit protein: $resultValue"
                "profit" -> resultText.text = "Profit: $resultValue"
            }
        } else {
            resultText.setVisibility(View.GONE)
        }

        var yieldValue = yield.toFloat()
        yieldValue /= 100
        val decimalFormat = DecimalFormat("#.####")
        // Format the value using the DecimalFormat object
        val formattedValue = decimalFormat.format(yieldValue)


        yield = formattedValue.toString()

        yieldText.text = "Arrived yield : $yield mg/ml"
        timeText.text = "Time : $time hours"
        gluText.text = "Glucose : $glu grams"
        mgText.text = "MgSO₄ : $mg grams"
        naText.text = "Na₂HPO₄ : $na grams"
    }
}