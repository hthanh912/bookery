package com.ht.bookery.models

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("username")
    val username: String,
    val password: String
)