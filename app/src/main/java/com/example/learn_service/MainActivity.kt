package com.example.learn_service

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeTextView = findViewById(R.id.tvChangeText)

        btnStartService.setOnClickListener {
            startServiceTypeTwo()
        }

        btnStopService.setOnClickListener {

        }
    }

    private fun startServiceTypeOne() {
        val serviceTypeOne = Intent(this, ServiceTypeOne::class.java)
        serviceTypeOne.putExtra(USER_NAME, "Arun Kumar V")
        startService(serviceTypeOne)
    }

    private fun startServiceTypeTwo() {
        val serviceTypeTwo = Intent(this, ServiceTypeTwo::class.java)
        serviceTypeTwo.putExtra(USER_NAME, "Arun Kumar V")
        startService(serviceTypeTwo)
    }

    private fun startLearnService() {
        val learnServiceIntent = Intent(this, LearnService::class.java)
        learnServiceIntent.putExtra(USER_NAME, "Arun Kumar V")
        startService(learnServiceIntent)
    }

    companion object {
        const val USER_NAME: String = "user_name"
        var changeTextView: TextView? = null
    }
}
