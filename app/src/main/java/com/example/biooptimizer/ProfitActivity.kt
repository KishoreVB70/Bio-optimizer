package com.example.optimizer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.biooptimizer.R

class ProfitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profit)
        // Python Stuff
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        val py = Python.getInstance()
        val module = py.getModule("genetic")
        var resultFromPython = arrayOf<String>()

        val pythonArray = arrayOf(
            floatArrayOf(24f, 35f),
            floatArrayOf(0.012f, 0.082f),
            floatArrayOf(0.4f, 1f),
            floatArrayOf(0.4f, 1.8f)
        )

        //Elements
        var submitButton = findViewById<Button>(R.id.btnsubmit)
        var workingEdit = findViewById<EditText>(R.id.edtworking)
        var gluEdit = findViewById<EditText>(R.id.edtglu)
        var mgEdit = findViewById<EditText>(R.id.edtmg)
        var naEdit = findViewById<EditText>(R.id.edtna)
        var valueEdit = findViewById<EditText>(R.id.edtvalue)

        submitButton.setOnClickListener {
            val editTextList = listOf(workingEdit, gluEdit, mgEdit, naEdit, valueEdit)
            // Check if any EditText field is empty
            var isEmpty = false
            for (editText in editTextList) {
                if (editText.text.toString().isEmpty()) {
                    isEmpty = true
                }
            }
            if (isEmpty) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {

                var workingEditText = workingEdit.text
                var gluEditText = gluEdit.text
                var mgEditText = mgEdit.text
                var naEditText = naEdit.text
                var valueEditText = valueEdit.text

                try {
                    val result = module.callAttr(
                        "profit",
                        workingEditText,
                        gluEditText,
                        mgEditText,
                        naEditText,
                        valueEditText,
                        pythonArray
                    ).toJava(String::class.java)
                    resultFromPython = result.split(",").toTypedArray()
                } catch (e: PyException) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    println(e.message)
                }
                val intent = Intent(this@ProfitActivity, ResultActivity::class.java)
                Intent(this@ProfitActivity, ResultActivity::class.java)
                var time = resultFromPython[0]
                var mg = resultFromPython[1]
                var glu  = resultFromPython[2]
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
        }
    }
}