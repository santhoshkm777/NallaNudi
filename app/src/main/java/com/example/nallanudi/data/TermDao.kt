package com.example.nallanudi.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TermDao {
    @Query("SELECT * FROM words")
    fun getAllTerms(): Flow<List<Word>>

    @Query("""
        SELECT * FROM words 
        WHERE english LIKE '%' || :query || '%' 
        OR kannada LIKE '%' || :query || '%' 
        OR explanation LIKE '%' || :query || '%'
        OR transliteration LIKE '%' || :query || '%'
        OR subject LIKE '%' || :query || '%'
    """)
    fun searchTerms(query: String): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE subject = :subject")
    fun getTermsBySubject(subject: String): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE isFavorite = 1")
    fun getFavoriteTerms(): Flow<List<Word>>

    @Update
    suspend fun updateTerm(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<Word>)

    @Query("SELECT * FROM words ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomTerm(): Word?
}
