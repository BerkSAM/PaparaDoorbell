package com.sammy.paparadoorbell.data.source.network.response.recipes

import com.google.gson.annotations.SerializedName

data class RecipesResult(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("image")
    val image: String?
)