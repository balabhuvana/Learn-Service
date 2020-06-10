package com.example.learn_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import kotlinx.android.synthetic.main.activity_binding.*
import kotlinx.android.synthetic.main.activity_main.*

class BindingActivity : AppCompatActivity() {

    lateinit var messenger: Messenger
    private var bound: Boolean = false
    lateinit var connection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binding)

        connection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.i("-----> ", "onServiceDisconnected()")
                bound = false
            }

            override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
                Log.i("-----> ", "onServiceConnected()")
                messenger = Messenger(iBinder)
                bound = true
            }
        }

        btnMessenger.setOnClickListener {
            sendMessageToService()
        }
    }

    private fun sendMessageToService() {
        if (!bound) return
        val message: Message = Message.obtain(null, BindServiceWithMessenger.MSG_SAY_HELLO, 0, 0)
        messenger.send(message)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, BindServiceWithMessenger::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(connection)
            bound = false
        }
    }
}
