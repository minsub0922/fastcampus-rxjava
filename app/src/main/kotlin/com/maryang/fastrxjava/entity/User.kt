package com.maryang.fastrxjava.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    override val id: Long,
    @SerializedName("login")
    val userName: String,
    val name: String,
    val email: String,
    val company: String,
    val location: String,
    val bio: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val type: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date
) : Identifier, Parcelable
