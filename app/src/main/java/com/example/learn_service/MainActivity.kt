package com.example.learn_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mBound: Boolean = false
    private var connection: ServiceConnection? = null
    private var bindServiceTypeOne: BindServiceTypeOne? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeTextView = findViewById(R.id.tvChangeText)

        btnStartService.setOnClickListener {
            startServiceTypeTwo()
        }

        btnStopService.setOnClickListener {

        }

        btnBoundServiceOne.setOnClickListener {
            var result = bindServiceTypeOne?.changeTheTextIntoUppercase("Anand Kumar")
            Log.i("-----> ", "" + result)
        }
    }

    override fun onStart() {
        super.onStart()
        startBoundService()
    }

    override fun onStop() {
        super.onStop()
        Log.i("-----> ", "onStop()")
        unbindService(connection!!)
        mBound = false
    }

    private fun startBoundService() {

        connection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.i("-----> ", "onServiceDisconnected() -> disConnected")
                mBound = false
            }

            override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
                Log.i("-----> ", "onServiceConnected() -> connected")
                mBound = true
                val localBinder = iBinder as BindServiceTypeOne.LocalBinder
                bindServiceTypeOne = localBinder.getServiceInstance()
                val result = bindServiceTypeOne!!.changeTheTextIntoUppercase("Arunkumar V")
                Log.i("----->", "" + result)
            }
        }

        val bindServiceTypeOne = Intent(this, BindServiceTypeOne::class.java)
        bindService(bindServiceTypeOne, connection as ServiceConnection, Context.BIND_AUTO_CREATE)
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
