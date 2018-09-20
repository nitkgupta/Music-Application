package com.nitkarsh.nitkarshplayer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        println("Hello")
        var binder=LocalBinder()
        return binder
    }
    inner class LocalBinder : Binder() {
        val service: MyService
            get() = this@MyService
    }
}
