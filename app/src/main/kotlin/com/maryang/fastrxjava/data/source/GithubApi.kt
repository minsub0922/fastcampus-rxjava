package com.maryang.fastrxjava.data.source

import com.google.gson.JsonElement
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.*

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

    @PUT("user/starred/{owner}/{repo}")
    fun star(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable

    @DELETE("user/starred/{owner}/{repo}")
    fun unstar(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Completable
}
