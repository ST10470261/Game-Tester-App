package com.example.gametesterapp

// IMPORTANT: These imports fix the "Unresolved reference" errors
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DataScreen : AppCompatActivity() {
    private lateinit var etDate: EditText
    private lateinit var etMinutesPlayed: EditText
    private lateinit var spinnerGenre: Spinner
    private lateinit var ratingBarInput: RatingBar
    private lateinit var btnAddEntry: Button
    private lateinit var btnClearInputs: Button
    private lateinit var btnGoToDetails: Button

    // ActivityResultLauncher to handle result from DetailsView (e.g. reset)
    private val detailsViewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // 3. Check if the result is from DetailsView and if the action is to clear data
        if (result.resultCode == Activity.RESULT_OK) {
            val action = result.data?.getStringExtra("action")
            if (action == "clear_data") {
                gameSessionProcessor = GameSessionProcessor()
            }
        }
    }

    companion object {
        // Singleton-like approach to keep data alive between screens
        var gameSessionProcessor: GameSessionProcessor = GameSessionProcessor()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Ensure this layout name matches your actual XML filename exactly
        setContentView(R.layout.activity_data_screen)

        // Initialize views - matched exactly to your XML IDs
        etDate = findViewById(R.id.datePicker)
        etMinutesPlayed = findViewById(R.id.minutesInput)
        spinnerGenre = findViewById(R.id.GenreInput) // Capital G as per XML
        ratingBarInput = findViewById(R.id.ratingBarInput)
        btnAddEntry = findViewById(R.id.addEntryBtn)
        btnClearInputs = findViewById(R.id.clearBtn)
        btnGoToDetails = findViewById(R.id.viewDetailsBtn)

        // Set up Date Picker dialog
        etDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Populate the Genre Spinner from strings.xml
        ArrayAdapter.createFromResource(
            this,
            R.array.game_genres,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGenre.adapter = adapter
        }

        // Button Click Listeners
        btnAddEntry.setOnClickListener {
            addGameEntry()
        }

        btnClearInputs.setOnClickListener {
            clearInputFields()
        }

        btnGoToDetails.setOnClickListener {
            openDetailsView()
        }

        updateAddEntryButtonState()
    }

    private fun openDetailsView() {
        val intent = Intent(this, DetailsView::class.java)
        intent.putExtra("gameSessionProcessor", gameSessionProcessor)
        detailsViewLauncher.launch(intent)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDayOfMonth)
                etDate.setText(date)
            }, year, month, day)
        datePickerDialog.show()
    }

    private fun addGameEntry() {
        val date = etDate.text.toString().trim()
        val minutesPlayedStr = etMinutesPlayed.text.toString().trim()
        val genre = spinnerGenre.selectedItem.toString()
        val rating = ratingBarInput.rating.toInt() // Pulling value from RatingBar

        // Validation logic
        if (date.isEmpty() || minutesPlayedStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            return
        }

        if (rating == 0) {
            Toast.makeText(this, "Please provide a rating.", Toast.LENGTH_SHORT).show()
            return
        }

        val minutes: Int = try {
            val m = minutesPlayedStr.toInt()
            if (m <= 0) {
                Toast.makeText(this, "Minutes must be positive.", Toast.LENGTH_SHORT).show()
                return
            }
            m
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Minutes must be a valid number.", Toast.LENGTH_SHORT).show()
            return
        }

        // Add to the processor list
        val added = gameSessionProcessor.addEntry(date, minutes, genre, rating)

        if (added) {
            Toast.makeText(this, "Session saved! (${gameSessionProcessor.entryCount}/7)", Toast.LENGTH_SHORT).show()
            clearInputFields()
            updateAddEntryButtonState()
        } else {
            Toast.makeText(this, "Maximum 7 entries reached.", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearInputFields() {
        etDate.setText("")
        etMinutesPlayed.setText("")
        spinnerGenre.setSelection(0)
        ratingBarInput.rating = 0f // Resetting the RatingBar visual
    }

    private fun updateAddEntryButtonState() {
        // Disable button if the 7-entry limit is reached
        btnAddEntry.isEnabled = gameSessionProcessor.entryCount < 7
    }

    override fun onResume() {
        super.onResume()
        updateAddEntryButtonState()
    }
}