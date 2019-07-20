package com.maryang.fastrxjava.util

import android.util.Log
import com.maryang.fastrxjava.base.BaseApplication.Companion.TAG
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


object BackpressureSample {

    fun overBackpressure() {
//        Observable
//            .range(1, 999999999)
//            .doOnNext { Log.d(TAG, "send event $it") }
//            .observeOn(Schedulers.computation())
//            .subscribe {
//                Thread.sleep(100)
//                Log.d(TAG, "receive event $it")
//            }

        Flowable
            .range(1, 999999999)
            .onBackpressureBuffer()
            .doOnNext { Log.d(TAG, "send event $it") }
            .observeOn(Schedulers.computation())
            .subscribe {
                Thread.sleep(100)
                Log.d(TAG, "receive event $it")
            }
    }
}
