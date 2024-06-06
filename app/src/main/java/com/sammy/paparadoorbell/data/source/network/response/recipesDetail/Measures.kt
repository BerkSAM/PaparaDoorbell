package com.sammy.paparadoorbell.data.source.network.response.recipesDetail


import com.google.gson.annotations.SerializedName

data class Measures(
    @SerializedName("metric")
    val metric: Metric?,
    @SerializedName("us")
    val us: Us?
)