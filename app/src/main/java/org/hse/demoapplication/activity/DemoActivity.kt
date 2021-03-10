package org.hse.demoapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_demo.*
import org.hse.demoapplication.R

class DemoActivity : AppCompatActivity() {


    private val valueLimit = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        button1.setOnClickListener {
            clickButton1()
        }

        button2.setOnClickListener{
            clickButton2()
        }
    }

    private fun clickButton1() {
        if(!validateEditText()) return
        val numberText = inputTextEdit.text.toString()
        val number = try {numberText.toInt()} catch (e : NumberFormatException) {0}
        val list = Array(number) {i -> i+1}
        resultTextView.text = getString(R.string.resultTextView_result1, list.sum())
    }

    private fun clickButton2() {
        if(!validateEditText()) return
        val numberText = inputTextEdit.text.toString()
        val number = try {numberText.toInt()} catch (e : NumberFormatException) {0}
        val list : Array<Int> = Array(number+1) { i -> if (i % 2 == 1 || i == 0) 1 else i }
        val product = list.reduce {acc, i -> acc * i }
        resultTextView.text = getString(R.string.resultTextView_result2, product)
    }

    private fun validateEditText(): Boolean {
        val text = inputTextEdit.text.toString()
        var errorMessage : String? = null

        if(text.isEmpty())
            errorMessage = getString(R.string.inputEditText_error_no_text)
        else if(!text.matches(Regex("[0-9]+")))
            errorMessage = getString(R.string.inputEditText_error_not_only_digits)
        else if(text.toInt() > valueLimit){
            Toast.makeText(applicationContext,
                    getString(R.string.toast_valueTooBig),
                    Toast.LENGTH_LONG)
                    .show()
            return false
        }

        if (errorMessage != null) {
            inputTextEdit.requestFocus()
            inputTextEdit.error = errorMessage
            return false
        }
        return true
    }

}