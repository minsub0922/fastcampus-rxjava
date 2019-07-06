package com.maryang.fastrxjava.util

import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object Operators {
    fun <T> applySchedulers(observable: Single<T>): Single<T> {
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> applySchedulers(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    val schedulersTransformer = SingleTransformer<Any, Any> {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> applySchedulersRecycle(): SingleTransformer<T, T> {
        return schedulersTransformer as SingleTransformer<T, T>
    }
}

fun <T> Single<T>.applySchedulersExtension(): Single<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
