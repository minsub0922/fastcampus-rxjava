package com.maryang.fastrxjava.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GithubRepo(
    @SerializedName("id")
    override val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("private")
    val private: Boolean,
    @SerializedName("owner")
    val owner: GithubRepoUser,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
    @SerializedName("pushed_at")
    val pushedAt: Date,
    @Expose
    var star: Boolean = false
) : Identifier, Parcelable {
    @Parcelize
    data class GithubRepoUser(
        @SerializedName("id")
        override val id: Long,
        @SerializedName("login")
        val userName: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
    ) : Identifier, Parcelable
}
