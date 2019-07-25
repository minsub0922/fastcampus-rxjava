package com.maryang.fastrxjava.util

import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.function.BiConsumer


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

//    fun rxJavaExample() {
//        Single.concat(
//            Single.just(listOf(1, 1, 1)),
//            Single.just(listOf(2, 2))
//        ).subscribe {
//            // 1,1,1,2,2
//        }
//
//        Single.merge(
//            Single.just(listOf(1, 1, 1)),
//            Single.just(listOf(2, 2))
//        ).subscribe {
//            // 1,2,2,1,1
//        }
//
//        Single.zip<Boolean, Int, String>(
//            Single.just(true),
//            Single.just(1),
//            BiFunction { first, second ->
//                ""
//            }
//        ).subscribe({
//
//        }, {
//
//        })
//    }



    //이것은 객체 지향적인 방식이다 !
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

    fun <T> applySchedulersRecycle() =  schedulersTransformer as SingleTransformer<T, T>

    fun mapOperators(){

        listOf<GithubRepo>().find {
            it.star
        }?.run {

        }

        listOf(1,2,3).reduce{ acc, number ->
            // acc : 이전 값이 누적된 것
            acc + number
            // 0+1
            // 1+2
            // 1+2+3
        }
        listOf(1,2,3).fold(0){ acc, number ->
            // acc : 이전 값이 누적된 것
            acc + number
            // 0+1
            // 1+2
            // 1+2+3
        }
        listOf("","1212","12").fold(1){ acc, str ->
            acc + str.toInt()
        }
        // reduce fold 차이점 ? -> fold에는 초기값이 있고 반환값은 초기값으로 정한 객체의 타입이다.
        listOf(listOf(1,2,3)).flatten().run {

        }
        listOf(1,2,3).all {
            it == 1
        }.run {
            //하나 라도 조건에 맞지않으면 false 반환
        }
        listOf(1,2,3).any{
            it == 1
        }.run {
            //하나 라도 조건에 맞으면 true
        }
        listOf(1,1,2,3,4,5).distinct().run {
            //중복값을 삭제해 준다. -> 1,2,3,4,5
        }
        listOf(1,1,2,3,4,5).groupBy {
            it == 1
        }.run {
            //map(true) = listOf(1,1)
        }
    }

    fun thread(){
        Executors.newCachedThreadPool() // task가 들어오면 thread에게 일을 시킨다. ..-> task가 들어옴에 따라 thread를 계속 생성한다.
        Executors.newFixedThreadPool(5)      // 고정된 풀 갯수 안에서 돌린다 ! -> 일이 갑자기 생겨도 thread를 늘리진않는다.
    }

    fun rxJavaExample(){

        //한꺼번에 날리고 동시에 받겟다 !!
        Single.concat(
            Single.just(listOf(1,1,1)),
            Single.just(listOf(2,2))
        ).subscribe{
            //누가 먼저 끝날지는 모르지만
            //1,1,1 2,2 순서로 받겠다!!

        }
        Single.merge(
            Single.just(listOf(1,1,1)),
            Single.just(listOf(2,2))
        ).subscribe {
            //실제로 받는 순서대로 받는다 !
            //concat 처럼 순서대로 받아버리면 flatmap으로 대체가 된다.
        }
        Single.zip<Boolean, Int, String>(   //내가 넣은 인자는 Boolean, Int 인데 결과는 String으로 하겠다
            Single.just(true),
            Single.just(1),
            BiFunction { first, second ->
                ""
            }
        ).subscribe({}, {})
//        Observable.combineLatest(
//            Observable.just(true),
//            Observable.just(1),
//            Function{
//
//            }
//        )



        Single
            .just(true)
            //여기까지는 true를 반환하는 Single
            .map{false}
            // 여기부터는 false를 반환하는 Single
            //rxJava의 map은 collection의 map이랑은 전혀 다르지만 구현결과는 같다 !!
        Single
            .just(true)
            .flatMap{
                Single.just(false)
            }

        //editText 검색을 할때 400ms 기준으로 api쏘는거 !
        Single.just(true)


    }

}



fun <T> Single<T>.applySchedulersExtension(): Single<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
