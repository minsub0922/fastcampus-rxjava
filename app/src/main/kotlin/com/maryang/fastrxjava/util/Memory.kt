package com.maryang.fastrxjava.util

import android.util.Log
import android.widget.TextView
import com.maryang.fastrxjava.base.BaseApplication
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


object Memory {
    fun leakObservable(text: TextView) {
        Observable
            .interval(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { x ->
                Log.d(BaseApplication.TAG, "leakObservable $x")
                text.text = x.toString()
            }
    }
}
