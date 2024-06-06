package com.sammy.paparadoorbell.data.source.local.entity

data class Step(
    val equipment: List<Equipment?>?,
    val ingredients: List<Ingredient?>?,
    val length: Length?,
    val number: Int?,
    val step: String?
)