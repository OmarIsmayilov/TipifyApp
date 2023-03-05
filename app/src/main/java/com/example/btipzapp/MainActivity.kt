package com.example.btipzapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.btipzapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var split = 1
    private var emsal = 0.0
    private var tip = 0.0
    private var total = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.MainSecond)
        binding.textViewSplit.text = split.toString()


        customEmsal()

        calculateTip()

        binding.buttonInc.setOnClickListener {
                split += 1
                binding.textViewSplit.text = split.toString()

        }

        binding.buttonDec.setOnClickListener {
            if(split>=2){
                split -= 1
                binding.textViewSplit.text = split.toString()
            }

        }



    }

    fun calculateTip(){
        binding.buttonCalculate.setOnClickListener {
            total = binding.editTextTotal.text.toString()

            if(total.isEmpty()){
                Toast.makeText(applicationContext,"⚠Enter total amount⚠",Toast.LENGTH_SHORT).show()
                return@setOnClickListener }

            when(binding.radioGroup.checkedRadioButtonId) {
                R.id.radioButtonNormal-> emsal = 0.05
                R.id.radioButtonGood-> emsal = 0.1
                R.id.radioButtonPerfect-> emsal = 0.15
                R.id.radioButtonCustom -> {
                    val customNum = binding.editTextCustom.text.toString()
                    if (customNum.isEmpty()){
                        Toast.makeText(applicationContext,"⚠Choose tip⚠",Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    emsal = customNum.toDouble()/100
                }
            }
            tip = (total.toDouble()*emsal)
            output()
        }
    }

    @SuppressLint("SetTextI18n")
    fun output(){
        if (binding.switchCeil.isChecked){
            binding.textViewTip.text = "${kotlin.math.ceil(tip)} ₼"
            binding.textViewTotal.text = "${kotlin.math.ceil(total.toDouble() + tip)} ₼"
            binding.textViewPer.text = "${kotlin.math.ceil((total.toDouble() + tip)/split)} ₼"
        }else{
            binding.textViewTip.text = "${tip} ₼"
            binding.textViewTotal.text = "${total.toDouble() + tip} ₼"
            binding.textViewPer.text = "${(total.toDouble() + tip)/split} ₼"
        }


    }

    @SuppressLint("SetTextI18n")
    fun customEmsal(){
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioButtonCustom -> {
                    binding.radioButtonCustom.setText("")
                    binding.editTextCustom.visibility = View.VISIBLE
                }
                else->{
                    binding.radioButtonCustom.setText("  Custom")
                    binding.editTextCustom.visibility = View.INVISIBLE
                    binding.editTextCustom.setText("")

                }


            }
        }


    }
}