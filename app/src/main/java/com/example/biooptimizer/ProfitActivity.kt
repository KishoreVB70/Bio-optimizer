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
        var submitButton = findViewById<Button>(R.id.btnsubmit)
        var resultText = findViewById<TextView>(R.id.txtresult)

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
                    // Display a toast message indicating that all fields must be filled
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

                startActivity(Intent(this, ResultActivity::class.java))
            }

//            try {
//                val result = module.callAttr(
//                    "npy").toString()
//                var pro = result.split(",").toTypedArray()
//                resultText.text = pro[0]
//            } catch (e: PyException) {
//                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
//                println(e.message)
//            }
        }
    }



}