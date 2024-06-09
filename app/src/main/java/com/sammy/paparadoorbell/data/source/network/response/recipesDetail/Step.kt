package com.sammy.paparadoorbell.data.source.network.response.recipesDetail


import com.google.gson.annotations.SerializedName
import com.sammy.paparadoorbell.data.source.local.entity.Ingredient

data class Step(
    @SerializedName("equipment")
    val equipment: List<Equipment?>?,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient?>?,
    @SerializedName("length")
    val length: Length?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("step")
    val step: String?
)