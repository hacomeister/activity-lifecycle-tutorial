package com.hacomeister.activitylifecycletutorial

import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var timer : CountDownTimer
    private var isPaused : Boolean = false
    private var remainingTime : Long = 0L

    companion object {
        // These represent different important times
        // This is when the timer is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the timer
        const val COUNTDOWN_TIME = 10000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity-Trace", "onCreate method is called")

        if(savedInstanceState!=null){
            if(savedInstanceState.getLong("remaining_key")!=0L){
                restartTimer(savedInstanceState.getLong("remaining_key"))
            }
        }
        else {
            initTimer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        if(remainingTime!=0L){
            outState.putBoolean("timer_state", true)
            outState.putLong("remaining_key", remainingTime)
        } else{
            outState.putBoolean("timer_state", false)
        }

        super.onSaveInstanceState(outState)
        Log.i("MainActivity-Trace", "onSaveInstanceState method is called")
    }

    private fun initTimer(){
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                Log.i("MainActivity-TraceTimer", DateUtils.formatElapsedTime(millisUntilFinished/ONE_SECOND))
                remainingTime = millisUntilFinished
                tv_remaining_time.text = DateUtils.formatElapsedTime(millisUntilFinished/ONE_SECOND)
            }

            override fun onFinish() {
                Log.i("MainActivity-TraceTimer", "Timer is finished.")
                remainingTime = 0L
                tv_remaining_time.text = DateUtils.formatElapsedTime(remainingTime/ONE_SECOND)
            }

        }
        timer.start()
    }

    private fun restartTimer(remaining: Long){
        isPaused = false
        timer = object : CountDownTimer(remaining, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("MainActivity-TraceTimer", DateUtils.formatElapsedTime(millisUntilFinished/ONE_SECOND))
                remainingTime = millisUntilFinished
                tv_remaining_time.text = DateUtils.formatElapsedTime(millisUntilFinished/ONE_SECOND)
            }
            override fun onFinish() {
                Log.i("MainActivity-TraceTimer", "Timer is finished.")
                remainingTime = 0L
                tv_remaining_time.text = DateUtils.formatElapsedTime(remainingTime/ ONE_SECOND)
            }
        }
        timer.start()
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity-Trace", "onStart method is called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity-Trace", "onResume method is called")

        if(isPaused){
            restartTimer(remainingTime)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity-Trace", "onPause method is called")

        if(remainingTime!=0L){
            isPaused = true
            timer.cancel()
        }

    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity-Trace", "onStop method is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity-Trace", "onDestroy method is called")
    }



    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("MainActivity-Trace", "onRestoreInstanceState method is called")
    }
}

