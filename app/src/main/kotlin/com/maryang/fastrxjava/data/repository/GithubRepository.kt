package com.maryang.fastrxjava.data.repository

import com.maryang.fastrxjava.data.source.ApiManager
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import io.reactivex.Maybe
import io.reactivex.Single

class GithubRepository {

    private val api = ApiManager.githubApi

    fun getGithubRepos(): Single<List<GithubRepo>> =
        api.getRepos()

    fun getUser(): Maybe<User> =
        api.getUser()
}
