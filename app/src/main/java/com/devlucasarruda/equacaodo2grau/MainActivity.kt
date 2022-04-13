package com.devlucasarruda.equacaodo2grau

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    lateinit var edtA: EditText
    lateinit var edtB: EditText
    lateinit var edtC: EditText
    lateinit var btnCalculate: Button
    lateinit var bntClear: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtA = findViewById(R.id.edtA)
        edtB = findViewById(R.id.edtB)
        edtC = findViewById(R.id.edtC)
        btnCalculate = findViewById(R.id.btnCalculate)
        bntClear = findViewById(R.id.bntClear)

        btnCalculate.setOnClickListener {
            calculateEquation()
        }
        bntClear.setOnClickListener {
            ClearFields()
        }

    }

    fun calculateEquation(){
        println("Começou a calcular...")

        if(edtA.text.isEmpty() || edtB.text.isEmpty() || edtC.text.isEmpty()){
            Toast.makeText(this, "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val a: Float = edtA.text.toString().toFloat()
        val b: Float = edtB.text.toString().toFloat()
        val c: Float = edtC.text.toString().toFloat()

        val x1: Float
        val x2: Float

        val d: Float = (b.pow(2) ) - 4*a*c //delta
        if(d < 0){
            println ("Sem solução no conjunto dos números reais")
        } else{
            x1 = (-b + sqrt(d)) / (2*a)
            x2 = (-b - sqrt(d)) / (2*a)
            println(x1.toString())
            println(x2.toString())
        }
    }

    fun ClearFields(){
        edtA.text = null
        edtB.text = null
        edtC.text = null
    }
}