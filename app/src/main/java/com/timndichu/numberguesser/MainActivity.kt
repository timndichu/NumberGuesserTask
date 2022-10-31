//    NUMBER GUESSER
//    Check if numberGuessed matches random number
//    If correct:
//     - set tvOutputDisplay to indicate that user got the guess correct
//     - increment TotalCorrectGuesses+1
//     - stop timer and set the timeTaken
//     - save the numberGuessed and timeTaken in a list
//     - set visible: Time Taken text view
//     - show btnStart
//     - change text of btnStart to Play Again
//    Else:
//     - if numberGuessed < randomNumber: set tvOutputDisplay => "Number too low"
//     - if numberGuessed > randomNumber: set tvOutputDisplay => "Number too high"

package com.timndichu.numberguesser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText

import android.widget.TextView
import androidx.core.view.isVisible
import java.util.*

private const val TAG = "MainActivity"
private const val APPBAR_TITLE = "Number Guesser"

class MainActivity : AppCompatActivity() {
    private lateinit var btnStart: Button
    private lateinit var tvTimer: TextView
    private lateinit var tvOutputDisplay: TextView
    private lateinit var etNumberGuessed: EditText
    private lateinit var btnSubmitGuess: Button
    private lateinit var tvTotalCorrectGuesses: TextView
    private lateinit var tvClickButton: TextView

    private var randomNumber = 0
    //  number of correct Guesses
    private var counter = 0
    //  List to store correct Guesses and the respective time taken to guess correctly
    private var numbersList = mutableListOf<NumberGuessed>()
    //  start and end times
    private var startTime: Long = Date().time
    private var endTime: Long = Date().time
    //  Time taken in milliseconds
    private var timeTakenMs = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setting ActionBar Title
        val action = supportActionBar
        action!!.title = APPBAR_TITLE

        //initializing elements
        tvClickButton = findViewById(R.id.tvClickButton)
        btnStart = findViewById(R.id.btnStart)
        tvTimer = findViewById(R.id.tvTimer)
        tvOutputDisplay = findViewById(R.id.tvOutputDisplay)
        etNumberGuessed = findViewById(R.id.etNumberGuessed)
        btnSubmitGuess = findViewById(R.id.btnSubmitGuess)
        tvTotalCorrectGuesses = findViewById(R.id.tvTotalCorrectGuesses)

        disableSubmitButton()

//      setting listeners
        btnSubmitGuess.setOnClickListener {
            submitGuess()
        }

        btnStart.setOnClickListener {
            startGuessing()
        }

        etNumberGuessed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                tvOutputDisplay.text = "Choose a Number between 1 and 20"
            }
        })
    }

    //    set new random number
    private fun generateRandomNumber() {
        randomNumber = (1..20).random()
        Log.i(TAG, "$randomNumber")
    }


//  when user submits their answer
    private fun submitGuess() {

        if (etNumberGuessed.text.isEmpty()) {
            return
        }
        val numberGuessed = etNumberGuessed.text.trim().toString().toInt()
        if (numberGuessed > 20 || numberGuessed < 1) {
            tvOutputDisplay.text = "Please enter a number between 1 and 20"
            return
        }
        if (numberGuessed == randomNumber) {
            correctGuess(numberGuessed)

        } else if (numberGuessed < randomNumber) {

            tvOutputDisplay.text = "Number too low, try again"
        } else if (numberGuessed > randomNumber) {

            tvOutputDisplay.text = "Number too high, try again"
        }

    }

//    record stop time
//    disable submit button
//    prompt user to play again
    private fun correctGuess(numberGuessed:Int) {
        endTime = Date().time
        calculateTimeTaken()
        tvOutputDisplay.text = "Correct Number Guessed!"
        etNumberGuessed.isEnabled = false
        counter++
        tvTotalCorrectGuesses.text = counter.toString()
        val numGuessedObj = NumberGuessed(numberGuessed, timeTakenMs)
        numbersList.add(numGuessedObj)
        for (element in numbersList) {
            println(element)
            Log.d(TAG, element.toString())
        }
        disableSubmitButton()
        btnStart.isVisible = true
        btnStart.text = "Play Again?"
    }

    //    Generate random number
    //    hide btnStart
//    enable text view and submit button
    //    start Timer
    private fun startGuessing() {
        generateRandomNumber()
        btnStart.isVisible = false
        tvClickButton.isVisible = false
        btnSubmitGuess.isEnabled = true
        etNumberGuessed.isEnabled = true
        tvTimer.text = ""
        etNumberGuessed.text.clear()
        startTime = Date().time

    }


    //    disable submit button and text field on app start
    private fun disableSubmitButton() {
        btnSubmitGuess.isEnabled = false
        etNumberGuessed.isEnabled = false
    }

    //    calculate time elapsed from start of game to correct guess
    private fun calculateTimeTaken() {
        val timeTaken = endTime - startTime
        val timeTakenString = timeStringFromLong(timeTaken)
        tvTimer.text = "Time elapsed: $timeTakenString"
        timeTakenMs = timeTaken.toInt()

    }
    //    convert milliseconds to hrs, min, secs for display purposes
    private fun timeStringFromLong(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / (1000 * 60) % 60)
        val hours = (ms / (1000 * 60 * 60) % 24)
        return makeTimeString(hours, minutes, seconds)
    }

    //  helper to format time displayed to the user
    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


}