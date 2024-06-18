package com.ht.bookery.models

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("username")
    val username: String,

    @SerializedName("firstName")
    val firstName: String,
)