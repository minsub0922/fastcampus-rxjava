package com.maryang.fastrxjava.util

import android.util.Log
import com.maryang.fastrxjava.base.BaseApplication
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

object SchedulersThread {

    fun logSchedulersThread() {
        Log.d(BaseApplication.TAG, "current thread: ${Thread.currentThread().name}")

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just io thread: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.io())
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(
                    BaseApplication.TAG,
                    "just computation thread: ${Thread.currentThread().name}"
                )
            }
            .subscribeOn(Schedulers.computation())
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just trampoline thread: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.trampoline())
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just newThread: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just newThread2: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe()

        val thread = Executors.newSingleThreadExecutor()
        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just from: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.from(thread))
            .subscribe()

        Single.just(true)
            .doOnSuccess {
                Log.d(BaseApplication.TAG, "just from2: ${Thread.currentThread().name}")
            }
            .subscribeOn(Schedulers.from(thread))
            .subscribe()
    }
}