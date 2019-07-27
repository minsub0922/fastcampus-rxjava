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
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("description")
    val description: String

) : Identifier, Parcelable {
    @Parcelize
    data class Owner(
        @SerializedName("id")
        override val id: Long,
        @SerializedName("login")
        val userName: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
    ) : Identifier, Parcelable
}

