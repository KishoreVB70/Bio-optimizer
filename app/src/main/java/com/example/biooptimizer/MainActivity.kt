package com.example.biooptimizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        var submitButton = findViewById<Button>(R.id.btnsubmit)
        var resultText = findViewById<TextView>(R.id.txtresult)


        val py = Python.getInstance()
        val module = py.getModule("genetic")

        submitButton.setOnClickListener {
            try {
                val result = module.callAttr(
                    "npy").toString()
                var pro = result.split(",").toTypedArray()
                resultText.text = pro[0]
            } catch (e: PyException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                println(e.message)
            }
        }
    }
}