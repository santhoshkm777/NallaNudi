package com.example.nallanudi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terms")
data class Term(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val englishTerm: String,
    val kannadaMeaning: String,
    val transliteration: String = "", // English transliteration of kannadaMeaning
    val explanation: String,
    val pronunciation: String,
    val subject: String, // e.g., "Science", "Math", "Commerce"
    val isFavorite: Boolean = false,
    val example: String = ""
)
