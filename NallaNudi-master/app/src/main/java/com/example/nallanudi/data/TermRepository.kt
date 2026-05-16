package com.example.nallanudi.data

import kotlinx.coroutines.flow.Flow

class TermRepository(private val termDao: TermDao) {

    fun getAllTerms(): Flow<List<Word>> = termDao.getAllTerms()

    fun searchTerms(query: String): Flow<List<Word>> = termDao.searchTerms(query)

    fun getTermsBySubject(subject: String): Flow<List<Word>> = termDao.getTermsBySubject(subject)

    fun getFavoriteTerms(): Flow<List<Word>> = termDao.getFavoriteTerms()

    suspend fun updateTerm(word: Word) = termDao.updateTerm(word)

    suspend fun insertAll(words: List<Word>) = termDao.insertAll(words)

    suspend fun getRandomTerm(): Word? = termDao.getRandomTerm()
}
