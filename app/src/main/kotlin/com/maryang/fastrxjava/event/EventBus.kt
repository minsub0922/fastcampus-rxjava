package com.maryang.fastrxjava.event

import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

object EventBus {
    private val bus = PublishSubject.create<Event>()
    //아무 애가 아니라 Event라고 정의한 애만 받곘다.

    fun post(parameter: Event) {
        bus.onNext(parameter)
    }

    fun observe() =
        bus.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
}
