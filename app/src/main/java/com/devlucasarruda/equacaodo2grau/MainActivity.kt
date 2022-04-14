package com.devlucasarruda.equacaodo2grau

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    lateinit var edtA: EditText
    lateinit var edtB: EditText
    lateinit var edtC: EditText
    lateinit var btnCalculate: Button
    lateinit var bntClear: ImageButton
    lateinit var textView: TextView

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

    fun calculateEquation(){
        println("Começou a calcular...")

        if(edtA.text.isEmpty() || edtB.text.isEmpty() || edtC.text.isEmpty()){
            Toast.makeText(this, "Por favor preencha todos os campos!", Toast.LENGTH_SHORT).show()
            clearSolution()
            return
        }

        val a: Double = edtA.text.toString().toDouble()
        val b: Double = edtB.text.toString().toDouble()
        val c: Double = edtC.text.toString().toDouble()

        if(a == 0.0){
            Toast.makeText(this, "O valor de ax² não pode ser igual a zero!", Toast.LENGTH_SHORT).show()
            clearSolution()
            return
        }

        val x1: Double
        val x2: Double

        val d: Double = (b.pow(2) ) - 4*a*c //delta
        if(d < 0){
            textView.text = "Sem solução no conjunto dos números reais"
            return
        } else{
            x1 = (-b + sqrt(d)) / (2*a)
            x2 = (-b - sqrt(d)) / (2*a)
            println(x1.toString())
            println(x2.toString())
        }

        val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
        df.maximumFractionDigits = 340 //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

        val line1:String = getSign(a) + df.format(abs(a)).toString()+"x²" + getSign(b) + df.format(abs(b)).toString()+"x" + getSign(c) + df.format(abs(c)).toString() + "\n"

        val line2:String = "S = {" + df.format(x1).toString() + " ; " + df.format(x2).toString() + "} \n"
        val line3:String = "\u0394 = -" + "" +" \n" // >> \u0394 represents delta in the UTF-8 encoding
        val line4:String = " \n"


        var solution:String = line1 + line2 + line3 + line4
        textView.text = solution

    }

    fun clearFields(){
        edtA.text = null
        edtB.text = null
        edtC.text = null
    }

    fun clearSolution(){
        textView.text = "ax² + bx + c = 0"
    }

    fun getSign(value:Double):String{
        if(value < 0.0){
            return " - "
        }else{
            return " + "
        }
    }

}