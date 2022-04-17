package com.devlucasarruda.equacaodo2grau

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.italic
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var edtA: EditText
    private lateinit var edtB: EditText
    private lateinit var edtC: EditText
    private lateinit var btnCalculate: Button
    private lateinit var bntClear: ImageButton
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtA = findViewById(R.id.edtA)
        edtB = findViewById(R.id.edtB)
        edtC = findViewById(R.id.edtC)
        btnCalculate = findViewById(R.id.btnCalculate)
        bntClear = findViewById(R.id.bntClear)
        textView = findViewById(R.id.textView)

        btnCalculate.setOnClickListener {
            calculateEquation()
        }
        bntClear.setOnClickListener {
            clearFields()
        }

    }

    private fun calculateEquation(){
        //println("Started to calculate...")

        hideKeyboard()

        if(edtA.text.isEmpty() || edtB.text.isEmpty() || edtC.text.isEmpty()){
            Toast.makeText(this, getString(R.string.PleaseFillAllFields), Toast.LENGTH_SHORT).show()
            clearSolution()
            return
        }

        val a: Double = edtA.text.toString().toDouble()
        val b: Double = edtB.text.toString().toDouble()
        val c: Double = edtC.text.toString().toDouble()

        if(a == 0.0){
            Toast.makeText(this, getString(R.string.ax2CantBeZero), Toast.LENGTH_SHORT).show()
            clearSolution()
            return
        }

        var x1: Double
        var x2: Double

        val d: Double = (b.pow(2) ) - 4*a*c //delta
        if(d < 0){
            textView.text = getString(R.string.NoRealSolution)
            return
        } else{
            x1 = (-b + sqrt(d)) / (2*a)
            x2 = (-b - sqrt(d)) / (2*a)
            //println(x1.toString())
            //println(x2.toString())
        }

        //correctNegativeZero
        if(abs(x1) == 0.0){
            x1 = 0.0
        }
        if(abs(x2) == 0.0){
            x2 = 0.0
        }

        val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
        df.maximumFractionDigits = 340 //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

        val line1:String =
            if(abs(a) == 1.0){
                if(abs(b) == 1.0){
                    getNullSign(a) + "x²" + getSign(b) + "x" + getSign(c) + df.format(abs(c)).toString() + "\n"
                }else{ //abs(b) != 1.0
                    getNullSign(a) + "x²" + getSign(b) + df.format(abs(b)).toString() + "x" + getSign(c) + df.format(abs(c)).toString() + "\n"
                }
            }else if (a < 0.0){
                if(abs(b) == 1.0){
                    "-" + df.format(abs(a)).toString() + "x²" + getSign(b) + "x" + getSign(c) + df.format(abs(c)).toString() + "\n"
                }else{ //abs(b) != 1.0
                    "-" + df.format(abs(a)).toString() + "x²" + getSign(b) + df.format(abs(b)).toString() + "x" + getSign(c) + df.format(abs(c)).toString() + "\n"
                }
            }else{ // a > 0.0
                if(abs(b) == 1.0){
                    df.format(abs(a)).toString() + "x²" + getSign(b) + "x" + getSign(c) + df.format(abs(c)).toString() + "\n"
                }else{ //abs(b) != 1.0
                    df.format(abs(a)).toString() + "x²" + getSign(b) + df.format(abs(b)).toString() + "x" + getSign(c) + df.format(abs(c)).toString() + "\n"
                }
            }

        val line2:String = "\n  S = {" + df.format(x1).toString() + " ; " + df.format(x2).toString() + "} \n\n"

        val line3 = "\u0394 = b² - 4 . a . c \n" // >> \u0394 represents delta in the UTF-8 encoding
        val line4:String = "Δ = (" + df.format(b).toString() + ")² - 4 . (" + df.format(a).toString() + ") . (" + df.format(c).toString() + ") \n"
        val line5:String = when{
            a * c < 0.0 -> "Δ = " + df.format(b.pow(2)).toString() + " + " + df.format(abs(4*a*c)).toString() + " \n"
            else -> "Δ = " + df.format(b.pow(2)).toString() + " - " + df.format(abs(4*a*c)).toString() + " \n"
        }
        val line6:String = "Δ = " + df.format(d) + " \n\n"

        val line7 = " x\u2081 = (-b + \u221aΔ) / (2 . a) \n"
        val line8:String = if(a < 0.0) {
            " x\u2081 = (" + df.format(-b).toString() + " + " + df.format(sqrt(d)) + ") / (2 . (" + df.format(a).toString() + ")) \n"
        }else {
            " x\u2081 = (" + df.format(-b).toString() + " + " + df.format(sqrt(d)) + ") / (2 . " + df.format(a).toString() + ") \n"
        }
        val line9:String = if(a < 0.0) {
            " x\u2081 = " + df.format(-b + sqrt(d)).toString() + " / (" + df.format(2 * a).toString() + ") \n"
        }else {
            " x\u2081 = " + df.format(-b + sqrt(d)).toString() + " / " + df.format(2 * a).toString() + " \n"
        }
        val line10:String = " x\u2081 = " + df.format(x1).toString() + " \n\n"

        val line11 = " x\u2082 = (-b - \u221aΔ) / (2 . a) \n"
        val line12:String = if(a < 0.0) {
            " x\u2082 = (" + df.format(-b).toString() + " - " + df.format(sqrt(d)) + ") / (2 . (" + df.format(a).toString() + ")) \n"
        }else {
            " x\u2082 = (" + df.format(-b).toString() + " - " + df.format(sqrt(d)) + ") / (2 . " + df.format(a).toString() + ") \n"
        }
        val line13:String = if(a < 0.0) {
            " x\u2082 = " + df.format(-b - sqrt(d)).toString() + " / (" + df.format(2 * a).toString() + ") \n"
        }else {
            " x\u2082 = " + df.format(-b - sqrt(d)).toString() + " / " + df.format(2 * a).toString() + " \n"
        }
        val line14:String = " x\u2082 = " + df.format(x2).toString() + " \n\n"


        val solution:SpannableStringBuilder = SpannableStringBuilder()
            .bold { append(line1) }
            .bold { italic { append(line2) } }
            .append(line3)
            .append(line4)
            .append(line5)
            .bold { append(line6) }
            .append(line7)
            .append(line8)
            .append(line9)
            .bold { append(line10) }
            .append(line11)
            .append(line12)
            .append(line13)
            .bold { append(line14) }
        textView.text = solution
    }

    private fun clearFields(){
        hideKeyboard()

        edtA.text = null
        edtB.text = null
        edtC.text = null
    }

    private fun clearSolution(){
        textView.text = getString(R.string.SecondDegreeEquation)
    }

    private fun getSign(value:Double):String{
        return if(value < 0.0){
            " - "
        }else{
            " + "
        }
    }

    private fun getNullSign(value:Double):String{
        return if(value < 0.0){
            " - "
        }else{
            ""
        }
    }

    private fun hideKeyboard(){
        val view = this.currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}