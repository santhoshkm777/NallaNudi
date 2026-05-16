package com.example.nallanudi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val english: String,
    val kannada: String,
    val explanation: String,
    val subject: String,
    val transliteration: String = "",
    val pronunciation: String = "",
    val isFavorite: Boolean = false,
    val example: String = ""
)
