package com.maryang.fastrxjava.ui.repos

import android.util.Log
import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import com.maryang.fastrxjava.util.applySchedulersExtension
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class GithubReposViewModel {
    private val repository = GithubRepository()

    fun getGithubRepos(): Single<List<GithubRepo>> =
        repository.getGithubRepos()
            .subscribeOn(Schedulers.io())   //이전 stream에 대한 스레드 지정
            .observeOn(AndroidSchedulers.mainThread())  //아래에 대한 스레드 지정

    fun getUserRepo(): Maybe<User> =
        repository.getUser()
            .subscribeOn(Schedulers.io())   //이전 stream에 대한 스레드 지정
            .observeOn(AndroidSchedulers.mainThread())  //아래에 대한 스레드 지정

    fun updatetUser(): Completable =
        repository.updateUser()
            .subscribeOn(Schedulers.io())   //이전 stream에 대한 스레드 지정
            .observeOn(AndroidSchedulers.mainThread())  //아래에 대한 스레드 지정

//    fun getGithubRepos(
//        onResponse: (List<GithubRepo>) -> Unit,
//        onFailure: (Throwable) -> Unit
//    ) {
//        repository.getGithubRepos()
//            .enqueue(object : DefaultCallback<List<GithubRepo>>() {
//                override fun onResponse(
//                    call: Call<List<GithubRepo>>,
//                    response: Response<List<GithubRepo>>
//                ) {
//                    onResponse(response.body() ?: emptyList())
//                }
//
//                override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
//                    super.onFailure(call, t)
//                    onFailure(t)
//                }
//            })
//    }

    private val searchSubject = PublishSubject.create<Pair<String, Boolean>>()
    var searchText = ""

    fun searchGithubRepos(search: String) {
        searchSubject.onNext(search to true)
    }

    fun searchGithubRepos() {
        searchSubject.onNext(searchText to false)
    }

    fun searchGithubReposSubject() =
        searchSubject
            .debounce(400, TimeUnit.MILLISECONDS)
            .doOnNext { searchText = it.first }
            .map { it.second }
            .observeOn(AndroidSchedulers.mainThread())

    fun searchGithubReposObservable() =
        Single.create<List<GithubRepo>> { emitter ->
            repository.searchGithubRepos(searchText)
                .subscribe({
                    Log.d("GithubRepos2", "thread2: ${Thread.currentThread()}")
                    Completable.merge(
                        it.map { repo ->
                            repository.checkStar(repo.owner.userName, repo.name)
                                .doOnComplete { repo.star = true }
                                .onErrorComplete()
                                //Error Operator를 활용해서 에러가 발생하더라도 스트림이 끊기지 않도록 한다
                        }
                    ).subscribe {
                        emitter.onSuccess(it)
                    }
                }, {})
        }   .applySchedulersExtension() //이게 최고지
            .toObservable()
//            .compose(Operators.applySchedulers())   //아래의 두줄을 없앨 수 있다...
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())  // 이 두 줄을 없애고 싶다.

    private fun checkStar(owner: String, repo: String): Completable =
        repository.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
