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


        // Python
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }
        val py = Python.getInstance()
        val module = py.getModule("genetic")
        var resultFromPython = arrayOf<String>()
        val pythonArray = arrayOf(
            floatArrayOf(24f, 30f),
            floatArrayOf(0.012f, 0.082f),
            floatArrayOf(0.4f, 1f),
            floatArrayOf(0.4f, 1.8f)
        )

        // Elements
        var pureButton = findViewById<Button>(R.id.btnpure)
        var costButton = findViewById<Button>(R.id.btncost)
        var timeButton = findViewById<Button>(R.id.btntime)
        var profitButton = findViewById<Button>(R.id.btnprofit)
        var limitButton = findViewById<Button>(R.id.btnlimit)
        var masterButton = findViewById<Button>(R.id.btnmaster)

        // Action 1
        pureButton.setOnClickListener {
            try {
                val result = module.callAttr(
                    "pure", pythonArray).toString()
                resultFromPython = result.split(",").toTypedArray()
            } catch (e: PyException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                println(e.message)
            }

            val intent = Intent(this@MainActivity, ResultActivity::class.java)

            var time = resultFromPython[0]
            var mg = resultFromPython[1]
            var glu  = resultFromPython[2]
            var na = resultFromPython[3]
            var yield = resultFromPython[4]

            intent.putExtra("yield", yield)
            intent.putExtra("time", time)
            intent.putExtra("glu", glu)
            intent.putExtra("mg", mg)
            intent.putExtra("na", na)
            startActivity(intent)
        }

        // Action 2
        timeButton.setOnClickListener {
            try {
                val result = module.callAttr(
                    "time", pythonArray).toString()
                resultFromPython = result.split(",").toTypedArray()
            } catch (e: PyException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                println(e.message)
            }

            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            Intent(this@MainActivity, ResultActivity::class.java)
            var time = resultFromPython[0]
            var glu  = resultFromPython[1]
            var mg = resultFromPython[2]
            var na = resultFromPython[3]
            var yield = resultFromPython[4]
            var resultValue = resultFromPython[5]

            intent.putExtra("yield", yield)
            intent.putExtra("time", time)
            intent.putExtra("glu", glu)
            intent.putExtra("mg", mg)
            intent.putExtra("na", na)
            intent.putExtra("resultValue", resultValue)

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