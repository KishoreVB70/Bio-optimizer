package com.example.optimizer

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
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

        //Python
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }
        val py = Python.getInstance()
        val module = py.getModule("particle")
        var resultFromPython = arrayOf<String>()
        val pythonArray = arrayOf(
            floatArrayOf(24f, 30f),
            floatArrayOf(0.012f, 0.082f),
            floatArrayOf(0.4f, 1f),
            floatArrayOf(0.4f, 1.8f)
        )

        //Elements
        val submitButton = findViewById<Button>(R.id.btnsubmit)

        var workingLowEdit = findViewById<EditText>(R.id.edttimelow)
        var workingHighEdit = findViewById<EditText>(R.id.edttimehigh)

        var gluLowEdit = findViewById<EditText>(R.id.edtglulow)
        var gluHighEdit = findViewById<EditText>(R.id.edtgluhigh)

        var mgLowEdit = findViewById<EditText>(R.id.edtmglow)
        var mgHighEdit = findViewById<EditText>(R.id.edtmghigh)

        var naLowEdit = findViewById<EditText>(R.id.edtnalow)
        var naHighEdit = findViewById<EditText>(R.id.edtnahigh)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val languages = resources.getStringArray(R.array.Languages)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            adapter.setDropDownViewResource(android.R.layout.select_dialog_item)
            spinner.adapter = adapter
        }

        // Action
        submitButton.setOnClickListener {

            // Limits initialization
            val workingLowEditText = workingLowEdit.text.toString().takeIf { it.isNotEmpty() } ?: "20.01"
            val workingHighEditText = workingHighEdit.text.toString().takeIf { it.isNotEmpty() } ?: "32.0"

            val mgLowEditText = mgLowEdit.text.toString().takeIf { it.isNotEmpty() } ?: "0.015"
            val mgHighEditText = mgHighEdit.text.toString().takeIf { it.isNotEmpty() } ?: "0.082"

            val gluLowEditText = gluLowEdit.text.toString().takeIf { it.isNotEmpty() } ?: "0.15"
            val gluHighEditText = gluHighEdit.text.toString().takeIf { it.isNotEmpty() } ?: "2.0"

            val naLowEditText = naLowEdit.text.toString().takeIf { it.isNotEmpty() } ?: "0.045"
            val naHighEditText = naHighEdit.text.toString().takeIf { it.isNotEmpty() } ?: "1.8"

            val workingLowFloat = workingLowEditText.toFloat()
            val workingHighFloat = workingHighEditText.toFloat()

            val mgLowFloat = mgLowEditText.toFloat()
            val mgHighFloat = mgHighEditText.toFloat()

            val gluLowFloat = gluLowEditText.toFloat()
            val gluHighFloat = gluHighEditText.toFloat()

            val naLowFloat = naLowEditText.toFloat()
            val naHighFloat = naHighEditText.toFloat()

            var lowerDefaultValues = arrayOf(20.0, 0.014, 0.14, 0.044)


            val editTexts = arrayOf(
                workingLowFloat,
                mgLowFloat,
                gluLowFloat,
                naLowFloat
            )

            var isGood = true

            for (i in editTexts.indices) {
                val inputValue = editTexts[i]
                if (inputValue < lowerDefaultValues[i]) {
                    isGood = false
                    Toast.makeText(
                        this,
                        "Enter lower bound higher than ${lowerDefaultValues[i]}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (isGood) {
                val pythonArray = arrayOf(
                    floatArrayOf(workingLowFloat, workingHighFloat),
                    floatArrayOf(mgLowFloat, mgHighFloat),
                    floatArrayOf(gluLowFloat, gluHighFloat),
                    floatArrayOf(naLowFloat, naHighFloat)
                )

                if (workingLowFloat > workingHighFloat ||
                    mgLowFloat > mgHighFloat ||
                    gluLowFloat > gluHighFloat ||
                    naLowFloat > naHighFloat
                ) {
                    Toast.makeText(this, "Lower bounds cannot be greater than upper bounds", Toast.LENGTH_SHORT).show()
                } else {
                    val selectedItem = spinner.selectedItem as String
                    when (selectedItem) {
                        "Pure optimization" -> {
                            try {
                                val result = module.callAttr(
                                    "pure", pythonArray
                                ).toString()
                                resultFromPython = result.split(",").toTypedArray()
                            } catch (e: PyException) {
                                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                                println(e.message)
                            }

                            val intent = Intent(this@MasterActivity, ResultActivity::class.java)

                            var time = resultFromPython[0]
                            var mg = resultFromPython[1]
                            var glu = resultFromPython[2]
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
                                    "time", pythonArray
                                ).toString()
                                resultFromPython = result.split(",").toTypedArray()
                            } catch (e: PyException) {
                                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                                println(e.message)
                            }

                            val intent = Intent(this@MasterActivity, ResultActivity::class.java)
                            Intent(this@MasterActivity, ResultActivity::class.java)
                            var time = resultFromPython[0]
                            var mg = resultFromPython[1]
                            var glu = resultFromPython[2]
                            var na = resultFromPython[3]
                            var yield = resultFromPython[4]
                            var resultValue = resultFromPython[5]

                            intent.putExtra("yield", yield)
                            intent.putExtra("time", time)
                            intent.putExtra("glu", glu)
                            intent.putExtra("mg", mg)
                            intent.putExtra("na", na)
                            intent.putExtra("resultValue", resultValue)
                            intent.putExtra("optimizer", "time")

                            startActivity(intent)
                        }

                        "Cost optimization" -> {
                            val intent = Intent(this@MasterActivity, CostActivity::class.java)
                            intent.putExtra("workingLowFloat", workingLowFloat)
                            intent.putExtra("workingHighFloat", workingHighFloat)
                            intent.putExtra("mgLowFloat", mgLowFloat)
                            intent.putExtra("mgHighFloat", mgHighFloat)
                            intent.putExtra("gluLowFloat", gluLowFloat)
                            intent.putExtra("gluHighFloat", gluHighFloat)
                            intent.putExtra("naLowFloat", naLowFloat)
                            intent.putExtra("naHighFloat", naHighFloat)
                            startActivity(intent)
                        }

                        "Profit optimization" -> {
                            val intent = Intent(this@MasterActivity, ProfitActivity::class.java)
                            intent.putExtra("workingLowFloat", workingLowFloat)
                            intent.putExtra("workingHighFloat", workingHighFloat)
                            intent.putExtra("mgLowFloat", mgLowFloat)
                            intent.putExtra("mgHighFloat", mgHighFloat)
                            intent.putExtra("gluLowFloat", gluLowFloat)
                            intent.putExtra("gluHighFloat", gluHighFloat)
                            intent.putExtra("naLowFloat", naLowFloat)
                            intent.putExtra("naHighFloat", naHighFloat)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}