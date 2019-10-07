package com.example.interfelltest.dblogic.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contravention(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val plate: String,
    val dateTime: String,
    val isYearsOrDiscapacy: Boolean,
    var isContravention: Boolean
)