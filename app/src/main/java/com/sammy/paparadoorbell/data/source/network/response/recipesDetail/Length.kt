package com.sammy.paparadoorbell.data.source.network.response.recipesDetail


import com.google.gson.annotations.SerializedName

data class Length(
    @SerializedName("number")
    val number: Int?,
    @SerializedName("unit")
    val unit: String?
)