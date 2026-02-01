package com.example.gametesterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var startBtn: Button
    private lateinit var exitBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        startBtn = findViewById(R.id.startBtn)
        exitBtn = findViewById(R.id.exitBtn)



        startBtn?.setOnClickListener {
            startActivity(Intent(this, DataScreen::class.java))
        }

        exitBtn?.setOnClickListener {
            finishAffinity()
        }
    }
}