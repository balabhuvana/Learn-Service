package com.example.learn_service

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log


class ServiceTypeTwo : Service() {

    private var serviceHandler: ServiceHandler? = null
    private var serviceLooper: Looper? = null

    inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Thread.sleep(5000)
            stopSelf()
        }
    }

    override fun onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.hasExtra(MainActivity.USER_NAME)!!) {
            Log.i(TAG, "" + intent.getStringExtra(MainActivity.USER_NAME))
        }

        val message: Message? = serviceHandler?.obtainMessage()
        message?.arg1 = startId
        serviceHandler!!.sendMessage(message)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy()")
        super.onDestroy()
    }

    companion object {
        private var TAG: String = ServiceTypeTwo::class.java.simpleName
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
