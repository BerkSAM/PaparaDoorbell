package com.sammy.paparadoorbell.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class LocalRecipes(
    @PrimaryKey val id: Int?,
    var image: String?,
    var title: String?
)