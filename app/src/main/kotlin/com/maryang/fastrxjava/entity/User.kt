package com.maryang.fastrxjava.entity

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long,
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val type: String
)
