package com.maryang.fastrxjava.data.source

import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun searchRepos(
        @Query("q") search: String
    ): Single<JsonElement>
    @GET("users/{userName}/repos")
    fun getRepos(
        @Path("userName") userName: String = "googlesamples"
    ): Single<List<GithubRepo>>

    @GET("user/starred/{owner}/{repo}")
    fun checkStar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable
    @GET("users/{userName}")
    fun getUser(
        @Path("userName") userName: String = "octocat"
    ): Maybe<User>

    @POST("users/{userName}")
    fun updatteUser(
        @Path("userName") userName: String = "octocat"
    ): Completable
}
