package com.tidfortrening

import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var submitButton: Button

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    message.setText(R.string.title_home)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    message.setText(R.string.title_dashboard)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    message.setText(R.string.title_notifications)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        Log.i(TAG, "Starting app")

        startButton = findViewById(R.id.button_start)
        stopButton = findViewById(R.id.button_stop)
        resetButton = findViewById(R.id.button_reset)
        submitButton = findViewById(R.id.button_submit)

        val chronometer = findViewById<Chronometer>(R.id.timer)
        val responseView = findViewById<TextView>(R.id.response)
        var resume = false
        var stopTime = 0L

        val queue = Volley.newRequestQueue(this)
        val apiUrl = "http://192.168.8.108:8080/activity/read/1"

        startButton.setOnClickListener {
            if (resume) {
                resume = false
                val timeStopped = SystemClock.elapsedRealtime() - stopTime
                chronometer.base = chronometer.base + timeStopped
            } else {
                chronometer.base = SystemClock.elapsedRealtime()
            }
            chronometer.start()
        }

        stopButton.setOnClickListener {
            chronometer.stop()
            stopTime = SystemClock.elapsedRealtime()
            resume = true
        }

        resetButton.setOnClickListener {
            chronometer.stop()
            resume = false
        }

        submitButton.setOnClickListener {
            Log.i(TAG, "Trying to submit")
            val jsonObjectRequest = JsonObjectRequest(
                GET, apiUrl, null,
                Response.Listener { response ->
                    Log.i(TAG,"Here be response $response")
                    responseView.text = "Response: %s".format(response.toString())
                },
                Response.ErrorListener { error ->
                    Log.e(TAG, "Error: $error")
                    responseView.text = "Something went wrong :("
                }
            )
            Log.i(TAG, "Adding to queue")
            queue.add(jsonObjectRequest)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
