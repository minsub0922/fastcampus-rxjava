package com.maryang.fastrxjava.ui

import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.data.source.DefaultCallback
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response

class GithubReposViewModel {
    private val repository = GithubRepository()

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


}
