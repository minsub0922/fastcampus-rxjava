package com.maryang.fastrxjava.util

import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


object Operators {
    fun kotlinOperators() {
        listOf<User>().let {
            it.forEach {

            }
            1
        }
        with(listOf<User>()) {
            forEach {

            }
            1
        }
        listOf<User>().run {
            forEach {

            }
            1
        }
    }

    fun collectionsExmaple() {
        // 기존 방식
        val list = listOf<User>()
        val list2 = mutableListOf<String>()
        list.forEach {
            list2.add(it.name)
        }

        // map
        listOf<User>().map {
            listOf(it.name)
        }

        // flatmap
        listOf<User>().flatMap {
            listOf(it.name)
        }

        // flatmap
        listOf<GithubRepo>().filter {
            it.star
        }

        listOf<GithubRepo>().find {
            it.star
        }

        // first
        // number = 1
        // acc = 0
        // acc + number = 1

        // second
        // number = 2
        // acc = 1
        // acc + number = 3

        // third
        // number = 3
        // acc = 3
        // acc + number = 6
        listOf(1, 2, 3).reduce { acc, number ->
            acc + number
        }

        // first
        // initial = 10
        // number = 1
        // acc = sample
        // initial + acc + number = 11

        // second
        // number = 2
        // acc = 11
        // acc + number = 13

        // third
        // number = 3
        // acc = 13
        // acc + number = 16
        listOf(1, 2, 3).fold(10f) { acc, number ->
            acc + number
        }

        listOf(listOf(1, 2, 3)).flatten()

        listOf(1, 2, 3).all { it == 1 }
        listOf(1, 2, 3).any { it == 1 }

        listOf(1, 1, 2, 2, 3, 4, 5).distinct()

        listOf(1, 1, 2, 2, 3, 4, 5).groupBy {
            it == 1
        }.run {
            // map(true) = listOf(1,1)
            // map(false) = listOf(2,2,3,4,5)
        }
    }

    fun rxJavaExample() {
        Single.concat(
            Single.just(listOf(1, 1, 1)),
            Single.just(listOf(2, 2))
        ).subscribe {
            // 1,1,1,2,2
        }

        Single.merge(
            Single.just(listOf(1, 1, 1)),
            Single.just(listOf(2, 2))
        ).subscribe {
            // 1,2,2,1,1
        }

        Single.zip<Boolean, Int, String>(
            Single.just(true),
            Single.just(1),
            BiFunction { first, second ->
                ""
            }
        ).subscribe({

        }, {

        })
    }

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
