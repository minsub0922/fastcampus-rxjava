package com.maryang.fastrxjava.util

import android.util.Log
import com.maryang.fastrxjava.base.BaseApplication.Companion.TAG
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


object EventBus {

    val bus = PublishSubject.create<Any>()

    fun post(parameter: Any){   //나는 이벤트를 날리겠다
        bus.onNext(parameter)
    }
    fun subscribe(observer: (Any) -> Unit){ //나는 받겠다.
        bus.subscribe{observer.invoke(it)}
    }
}
