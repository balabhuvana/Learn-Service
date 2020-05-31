package com.example.learn_service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.widget.Toast

class LearnService : Service() {

    private var mHandlerThread: Handler? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        sampleHandlerMethod()

        val message = Message()
        message.what = START_DOWNLOAD
        mHandlerThread?.sendMessage(message)

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    private fun sampleHandlerMethod() {
        mHandlerThread = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    START_DOWNLOAD -> startDownload()
                    DOWNLOAD_COMPLETE -> downloadComplete()
                }
            }
        }
    }

    private fun startDownload() {
        Thread.sleep(5000)
        mHandlerThread?.sendEmptyMessage(DOWNLOAD_COMPLETE)
    }

    private fun downloadComplete() {
        MainActivity.changeTextView?.text = "Download Complete"
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val START_DOWNLOAD = 100
        private const val DOWNLOAD_COMPLETE = 101
    }

}