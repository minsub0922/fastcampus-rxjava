package com.maryang.fastrxjava.data.source

import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{userName}/repos")
    fun getRepos(
        @Path("userName") userName: String = "googlesamples"
    ): Call<List<GithubRepo>>

    @GET("users/{userName}")
    fun getUser(
        @Path("userName") userName: String = "octocat"
    ): Call<User>
}
