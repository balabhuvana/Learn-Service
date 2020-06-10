package com.example.learn_service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log

class BindServiceWithMessenger : Service() {


    private lateinit var messenger: Messenger

    inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {

            when (msg.what) {
                MSG_SAY_HELLO -> displayName("Arunkumar V")
                else ->
                    super.handleMessage(msg)
            }
        }
    }

    fun displayName(name: String) {
        Log.i("-----> ", "" + name.toUpperCase())
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i("-----> ", "onBind()")
        messenger = Messenger(MyHandler())
        return messenger.binder
    }

    companion object {
        const val MSG_SAY_HELLO = 10001
    }
}
