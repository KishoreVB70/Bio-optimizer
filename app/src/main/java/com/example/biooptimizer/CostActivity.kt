package com.example.optimizer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.biooptimizer.R
import javax.xml.transform.Result

class CostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cost)
        var submitButton = findViewById<Button>(R.id.btnsubmit)
        var resultText = findViewById<TextView>(R.id.txtresult)

        var workingEdit = findViewById<EditText>(R.id.edtworking)
        var gluEdit = findViewById<EditText>(R.id.edtglu)
        var mgEdit = findViewById<EditText>(R.id.edtmg)
        var naEdit = findViewById<EditText>(R.id.edtna)
        submitButton.setOnClickListener {
            val editTextList = listOf(workingEdit, gluEdit, mgEdit, naEdit)
            var isEmpty = false
            // Check if any EditText field is empty
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

                startActivity(Intent(this, ResultActivity::class.java))
            }




//            if (! Python.isStarted()) {
//                Python.start(AndroidPlatform(this));
//            }
//            val py = Python.getInstance()
//            val module = py.getModule("genetic")

//            val result = module.callAttr("pureOptimization", workingEditText, gluEditText, mgEditText,naEditText, valueEditText).toInt()
//            resultText.text = "$result"
        }
    }
}