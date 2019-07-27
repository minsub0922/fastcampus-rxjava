package com.maryang.fastrxjava.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MyInfo(
    @SerializedName("id")
    override val id: Long,
    @SerializedName("login")
    val userName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("bio")
    val introduce: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("blog")
    val blogUrl: String,
    @SerializedName("avatar_url")
    val profileImageUrl: String

) : Identifier, Parcelable

