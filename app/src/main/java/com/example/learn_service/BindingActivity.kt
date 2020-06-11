package com.example.learn_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_binding.*


class BindingActivity : AppCompatActivity() {

    lateinit var messenger: Messenger
    private var bound: Boolean = false
    lateinit var connection: ServiceConnection
    lateinit var replyMessenger: Messenger

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
                replyMessenger = Messenger(HandlerForServiceMessage())
                bound = true
            }
        }

        btnMessenger.setOnClickListener {
            sendMessageToService()
        }

        btnMessengerWithReply.setOnClickListener {
            sendMessageToServiceAndGetReply()
        }
    }

    inner class HandlerForServiceMessage : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                BindServiceWithMessenger.LONG_PERFORMING_TASK_COMPLETE -> longPerformingTaskCompleted()
                else ->
                    super.handleMessage(msg)
            }
            super.handleMessage(msg)
        }
    }

    fun longPerformingTaskCompleted() {
        Log.i("-----> ", "longPerformingTaskCompleted")
    }

    private fun sendMessageToService() {
        if (!bound) return
        val message: Message = Message.obtain(null, BindServiceWithMessenger.MSG_SAY_HELLO, 0, 0)
        messenger.send(message)
    }


    private fun sendMessageToServiceAndGetReply() {
        if (!bound) return
        val message: Message = Message.obtain(null, BindServiceWithMessenger.LONG_PERFORMING_TASK, 0, 0)
        message.replyTo = replyMessenger
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
