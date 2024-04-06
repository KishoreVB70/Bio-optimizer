package com.example.optimizer

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.biooptimizer.R

class MasterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        //Elements
        val spinner = findViewById<Spinner>(R.id.spinner)
        val submitButton = findViewById<Button>(R.id.btnsubmit)



        val languages = resources.getStringArray(R.array.Languages)

        // access the spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter
        }

        submitButton.setOnClickListener {
            val selectedItem = spinner.selectedItem as String
            val intent = when (selectedItem) {
                "Pure optimization" -> Intent(this, ResultActivity::class.java)
                "Time optimization" -> Intent(this, ResultActivity::class.java)
                "Cost optimization" -> Intent(this, CostActivity::class.java)
                "Profit optimization" -> Intent(this, ProfitActivity::class.java)
                else -> null
            }
            intent?.let { startActivity(it) }
        }
    }
}