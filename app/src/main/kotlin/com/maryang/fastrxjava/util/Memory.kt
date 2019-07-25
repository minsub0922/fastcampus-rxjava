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

        //노란 이유???
        //Comsumer를 사용했기 때문에 Disposable이 반환된다

        //Observer를 사용할 때 Disposable을 받고싶으면 subscribeWith를 사용한다
        //그렇지 않으면 void이다
    }
}
