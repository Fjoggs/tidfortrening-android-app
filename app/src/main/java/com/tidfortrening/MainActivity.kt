package com.tidfortrening

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private lateinit var startButton: Button
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                textMessage.setText(R.string.title_running)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_lifting -> {
                textMessage.setText(R.string.title_lifting)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_friends -> {
                textMessage.setText(R.string.title_friends)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun startButtonOnClick(view: View) {
        val startDate: Date = Calendar.getInstance().time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        startButton = findViewById(R.id.startButton)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
