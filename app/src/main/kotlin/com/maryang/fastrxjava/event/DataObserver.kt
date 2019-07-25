package com.maryang.fastrxjava.event

import com.maryang.fastrxjava.entity.Identifier
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

object DataObserver {
    private val bus = PublishSubject.create<Identifier>()

    fun post(parameter: Identifier) {
        bus.onNext(parameter)
    }

    fun observe() =
        bus.observeOn(AndroidSchedulers.mainThread())
}
