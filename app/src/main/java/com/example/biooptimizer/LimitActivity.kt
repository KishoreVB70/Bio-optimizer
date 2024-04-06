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

class LimitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_limit)

        //Python
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }
        val py = Python.getInstance()
        val module = py.getModule("genetic")
        var resultFromPython = arrayOf<String>()


        // Elements
        var submitButton = findViewById<Button>(R.id.btnsubmit)
        var workingEdit = findViewById<EditText>(R.id.edtworking)
        var gluEdit = findViewById<EditText>(R.id.edtglu)
        var mgEdit = findViewById<EditText>(R.id.edtmg)
        var naEdit = findViewById<EditText>(R.id.edtna)

        //Action
        submitButton.setOnClickListener {
            val workingEditText = workingEdit.text.toString().takeIf { it.isNotEmpty() } ?: "32.0"
            val mgEditText = mgEdit.text.toString().takeIf { it.isNotEmpty() } ?: "0.082"
            val gluEditText = gluEdit.text.toString().takeIf { it.isNotEmpty() } ?: "1.0"
            val naEditText = naEdit.text.toString().takeIf { it.isNotEmpty() } ?: "1.8"

            val workingFloat = workingEditText.toFloat()
            val mgFloat = mgEditText.toFloat()
            val gluFloat = gluEditText.toFloat()
            val naFloat = naEditText.toFloat()

            val pythonArray = arrayOf(
                floatArrayOf(0f, workingFloat),
                floatArrayOf(0f, mgFloat),
                floatArrayOf(0f, gluFloat),
                floatArrayOf(0f, naFloat)
            )
            try {
                val result = module.callAttr(
                    "pure", pythonArray).toString()
                resultFromPython = result.split(",").toTypedArray()
            } catch (e: PyException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                println(e.message)
            }

            val intent = Intent(this@LimitActivity, ResultActivity::class.java)

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
    }
}