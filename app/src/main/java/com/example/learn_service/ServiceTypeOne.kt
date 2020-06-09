package com.example.learn_service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServiceTypeOne : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread.sleep(10000)
        if (intent?.hasExtra(MainActivity.USER_NAME)!!) {
            Log.i(TAG, "" + intent.getStringExtra(MainActivity.USER_NAME))
        }
        stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy()")
        super.onDestroy()
    }

    companion object {
        private var TAG: String = ServiceTypeOne::class.java.simpleName
    }
}
