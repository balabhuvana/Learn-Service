package com.example.learn_service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BindServiceWithExtends : Service() {

    var localBinder: LocalBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getServiceInstance(): BindServiceWithExtends {
            return this@BindServiceWithExtends
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return localBinder
    }

    fun changeTheTextIntoUppercase(data: String): String {
        return data.toUpperCase()
    }
}
