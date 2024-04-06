package com.example.optimizer

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.biooptimizer.R

class MasterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }
        val py = Python.getInstance()
        val module = py.getModule("genetic")

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

        var resultFromPython = arrayOf<String>()

        val pythonArray = arrayOf(
            floatArrayOf(24f, 30f),
            floatArrayOf(0.012f, 0.082f),
            floatArrayOf(0.4f, 1f),
            floatArrayOf(0.4f, 1.8f)
        )

        submitButton.setOnClickListener {
            val selectedItem = spinner.selectedItem as String
            when (selectedItem) {
                "Pure optimization" -> {
                    try {
                        val result = module.callAttr(
                            "pure", pythonArray).toString()
                        resultFromPython = result.split(",").toTypedArray()
                    } catch (e: PyException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                        println(e.message)
                    }

                    val intent = Intent(this@MasterActivity, ResultActivity::class.java)

                    var time = resultFromPython[0]
                    var glu  = resultFromPython[1]
                    var mg = resultFromPython[2]
                    var na = resultFromPython[3]
                    var yield = resultFromPython[4]

                    intent.putExtra("yield", yield)
                    intent.putExtra("time", time)
                    intent.putExtra("glu", glu)
                    intent.putExtra("mg", mg)
                    intent.putExtra("na", na)
                    startActivity(intent)
                }

                "Time optimization" -> {
                    try {
                        val result = module.callAttr(
                            "time", pythonArray).toString()
                        resultFromPython = result.split(",").toTypedArray()
                    } catch (e: PyException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                        println(e.message)
                    }

                    val intent = Intent(this@MasterActivity, ResultActivity::class.java)
                    Intent(this@MasterActivity, ResultActivity::class.java)
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
                "Cost optimization" -> {
                    startActivity(Intent(this, CostActivity::class.java))
                }
                "Profit optimization" -> {
                    startActivity(Intent(this, ProfitActivity::class.java))
                }
            }
        }
    }
}