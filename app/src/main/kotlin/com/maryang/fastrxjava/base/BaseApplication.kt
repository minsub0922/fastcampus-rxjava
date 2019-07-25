package com.maryang.fastrxjava.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.facebook.stetho.Stetho

class BaseApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
        const val TAG = "FastRxJava2"
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Stetho.initializeWithDefaults(this)
    }

}
