package com.maryang.fastrxjava.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    @SerializedName("id")
    override val id: Long,
    @SerializedName("login")
    val userName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date
) : Identifier, Parcelable
