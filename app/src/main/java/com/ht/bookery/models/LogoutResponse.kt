package com.ht.bookery.models

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: Int,
)