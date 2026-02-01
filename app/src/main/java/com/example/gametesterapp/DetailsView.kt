package com.example.gametesterapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailsView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_view)

        // 1. Get the Processor from the Intent
        val processor = intent.getParcelableExtra<GameSessionProcessor>("gameSessionProcessor")

        if (processor != null) {
            // 2. Set the Average Minutes
            val avg = processor.calculateAverageMinutes()
            findViewById<TextView>(R.id.averageMinTxt).text = String.format("%.1f mins", avg)

            // 3. Set the Highest Rated Genre
            val highest = processor.findHighestRatedGame()
            findViewById<TextView>(R.id.genreTxt).text = "${highest.first} (${highest.second}/5)"

            // 4. Set Total Sessions
            findViewById<TextView>(R.id.sessionsTxt).text = processor.entryCount.toString()
        } else {
            findViewById<TextView>(R.id.averageMinTxt).text = "--"
            findViewById<TextView>(R.id.genreTxt).text = "--"
            findViewById<TextView>(R.id.sessionsTxt).text = "--"
        }

        // Buttons
        findViewById<Button>(R.id.backBtn).setOnClickListener { finish() }
        findViewById<Button>(R.id.exitBtn).setOnClickListener { finishAffinity() }
        findViewById<Button>(R.id.resetBtn).setOnClickListener {


            // 1. Create an Intent to send a result back
            val resultIntent = Intent()
            resultIntent.putExtra("action", "clear_data")

            // 2. Set the result and finish this activity
            setResult(Activity.RESULT_OK, resultIntent)
            Toast.makeText(this, "Data has been cleared", Toast.LENGTH_SHORT).show()
            finish() // Close the DetailsView and go back to the previous screen
        }
    }

}