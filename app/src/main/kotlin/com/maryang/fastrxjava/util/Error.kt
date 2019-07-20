package com.maryang.fastrxjava.util

import android.util.Log
import com.maryang.fastrxjava.base.BaseApplication
import io.reactivex.Observable

class Error {

    fun error() {
        Observable.error<Unit>(IllegalStateException())
            .subscribe({}, {
                Log.d(BaseApplication.TAG, "error $it")
            })
    }
}
