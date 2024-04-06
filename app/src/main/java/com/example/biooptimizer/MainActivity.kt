package com.example.biooptimizer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.optimizer.CostActivity
import com.example.optimizer.LimitActivity
import com.example.optimizer.MasterActivity
import com.example.optimizer.ProfitActivity
import com.example.optimizer.ResultActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        // Getting the button elements
        var pureButton = findViewById<Button>(R.id.btnpure)
        var costButton = findViewById<Button>(R.id.btncost)
        var timeButton = findViewById<Button>(R.id.btntime)
        var profitButton = findViewById<Button>(R.id.btnprofit)
        var limitButton = findViewById<Button>(R.id.btnlimit)
        var masterButton = findViewById<Button>(R.id.btnmaster)

        // Listeners
        pureButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            var yield = 50
            var time = 30
            var glu  = 0.8
            var mg = 0.018
            var na = 1.4

            intent.putExtra("yield", yield.toString())
            intent.putExtra("time", time.toString())
            intent.putExtra("glu", glu.toString())
            intent.putExtra("mg", mg.toString())
            intent.putExtra("na", na.toString())
            startActivity(intent)
        }

        timeButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            Intent(this@MainActivity, ResultActivity::class.java)
            var yield = 50
            var time = 30
            var glu  = 0.8
            var mg = 0.018
            var na = 1.4

            intent.putExtra("yield", yield.toString())
            intent.putExtra("time", time.toString())
            intent.putExtra("glu", glu.toString())
            intent.putExtra("mg", mg.toString())
            intent.putExtra("na", na.toString())
            startActivity(intent)
        }

        costButton.setOnClickListener {
            startActivity(Intent(this, CostActivity::class.java))
        }
        profitButton.setOnClickListener {
            startActivity(Intent(this, ProfitActivity::class.java))
        }
        limitButton.setOnClickListener {
            startActivity(Intent(this, LimitActivity::class.java))
        }
        masterButton.setOnClickListener {
            startActivity(Intent(this, MasterActivity::class.java))
        }
    }
}