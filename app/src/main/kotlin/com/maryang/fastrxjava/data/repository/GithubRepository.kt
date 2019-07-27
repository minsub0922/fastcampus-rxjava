package com.maryang.fastrxjava.data.repository

import com.maryang.fastrxjava.data.source.ApiManager
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.MyInfo
import com.maryang.fastrxjava.entity.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class GithubRepository {

    private val api = ApiManager.githubApi

    fun getMyInfo():Single<List<MyInfo>> =
            api.getMyInfo()

    fun getGithubRepos(): Single<List<GithubRepo>> =
        api.getRepos()

    fun getUser(): Maybe<User> =
        api.getUser()

    fun updateUser(): Completable =
        api.updatteUser()

    fun searchGithubRepos(q: String): Single<List<GithubRepo>> =
        api.searchRepos(q)
            .map {
                it.asJsonObject.getAsJsonArray("items")
                    .map{
                        ApiManager.gson.fromJson(it, GithubRepo::class.java)!!
                    }
            }
            .subscribeOn(Schedulers.io())

    fun checkStar(owner: String, repo: String): Completable =
        api.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun star(owner: String, repo: String): Completable =
        api.star(owner, repo)
            .subscribeOn(Schedulers.io())

    fun unstar(owner: String, repo: String): Completable =
        api.unstar(owner, repo)
            .subscribeOn(Schedulers.io())
}
