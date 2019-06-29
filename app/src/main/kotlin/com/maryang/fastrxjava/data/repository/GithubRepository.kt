package com.maryang.fastrxjava.data.repository

import com.maryang.fastrxjava.data.source.ApiManager
import com.maryang.fastrxjava.entity.GithubRepo
import retrofit2.Call

class GithubRepository {

    private val api = ApiManager.githubApi

    fun getGithubRepos(): Call<List<GithubRepo>> =
        api.getRepos()
}
