package com.sammy.paparadoorbell.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String?
)