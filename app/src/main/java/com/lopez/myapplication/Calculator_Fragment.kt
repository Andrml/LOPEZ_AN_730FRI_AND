package com.lopez.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class Calculator_Fragment : Fragment() {

    private lateinit var display: TextView
    private var firstNumber: Double = Double.NaN
    private var secondNumber: Double = Double.NaN
    private var operatorSelected: Boolean = false
    private var currentOperator: Char = ' '

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_calculator_, container, false)
        display = view.findViewById(R.id.editText)


        setupButtonListeners(view)

        return view
    }

    private fun setupButtonListeners(view: View) {

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9
        )

        buttons.forEachIndexed { index, buttonId ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                onNumberClick(it)
            }
        }

        view.findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick(it) }
        view.findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick(it) }
        view.findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick(it) }
        view.findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick(it) }
        view.findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqualClick(it) }
        view.findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick(it) }
    }

    fun onNumberClick(view: View) {
        val button = view as Button
        val number = button.text.toString()
        display.append(number)
    }

    fun onOperatorClick(view: View) {
        val button = view as Button
        val newOperator = button.text.toString()[0]

        if (operatorSelected && !firstNumber.isNaN()) {
            secondNumber = display.text.toString().toDouble()
            val result = calculate(firstNumber, secondNumber, currentOperator)

            display.text = String.format("%.2f", result)
            firstNumber = result
            secondNumber = Double.NaN
        } else {
            firstNumber = display.text.toString().toDouble()
        }
        currentOperator = newOperator
        operatorSelected = true
        display.text = ""
    }

    fun onEqualClick(view: View) {
        if (operatorSelected && !firstNumber.isNaN()) {
            if (secondNumber.isNaN()) {
                secondNumber = display.text.toString().toDouble()
            }
            val result = calculate(firstNumber, secondNumber, currentOperator)
            display.text = String.format("%.2f", result)
            reset()
        } else {
            display.error = "Incomplete input."
        }
    }

    fun onClearClick(view: View) {
        reset()
        display.text = ""
    }

    private fun calculate(num1: Double, num2: Double, operator: Char): Double {
        return when (operator) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> num1 / num2
            else -> 0.0
        }
    }

    private fun reset() {
        firstNumber = Double.NaN
        secondNumber = Double.NaN
        operatorSelected = false
        currentOperator = ' '
    }
}
