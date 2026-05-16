package com.example.nallanudi.ui

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nallanudi.data.AppDatabase
import com.example.nallanudi.data.Word
import com.example.nallanudi.data.TermRepository
import com.example.nallanudi.data.DictionaryData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class NallaNudiViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private val repository: TermRepository
    private var tts: TextToSpeech? = null
    private val speechState = mutableMapOf<Int, Int>()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedSubject = MutableStateFlow<String?>(null)
    val selectedSubject: StateFlow<String?> = _selectedSubject.asStateFlow()

    // Using a Flow to handle debounced search for better performance
    val searchResults: StateFlow<List<Word>> = combine(
        _searchQuery.debounce(300).distinctUntilChanged(),
        _selectedSubject
    ) { query, subject ->
        query to subject
    }.flatMapLatest { (query, subject) ->
        val trimmedQuery = query.trim()
        if (trimmedQuery.isEmpty()) {
            if (subject == null) {
                repository.getAllTerms()
            } else {
                repository.getTermsBySubject(subject)
            }
        } else {
            // Room LIKE query handles partial matching and is case-insensitive by default (depending on collation)
            repository.searchTerms(trimmedQuery)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _wordOfTheDay = MutableStateFlow<Word?>(null)
    val wordOfTheDay: StateFlow<Word?> = _wordOfTheDay.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = TermRepository(database.termDao())
        tts = TextToSpeech(application, this)
        
        viewModelScope.launch {
            repository.getAllTerms().first().let { terms ->
                if (terms.isEmpty()) {
                    seedData()
                }
            }
        }
        
        loadWordOfTheDay()
    }

    val favoriteTerms: Flow<List<Word>> = repository.getFavoriteTerms()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectSubject(subject: String?) {
        _selectedSubject.value = subject
    }

    fun toggleFavorite(word: Word) {
        viewModelScope.launch {
            repository.updateTerm(word.copy(isFavorite = !word.isFavorite))
        }
    }

    fun speak(word: Word) {
        val clickCount = speechState.getOrDefault(word.id, 0)
        if (clickCount % 2 == 0) {
            tts?.language = Locale("kn", "IN")
            tts?.speak(word.kannada, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            tts?.language = Locale.US
            tts?.speak(word.english, TextToSpeech.QUEUE_FLUSH, null, null)
        }
        speechState[word.id] = clickCount + 1
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
        }
    }

    private fun loadWordOfTheDay() {
        viewModelScope.launch {
            _wordOfTheDay.value = repository.getRandomTerm()
        }
    }

    private suspend fun seedData() {
        repository.insertAll(DictionaryData.getInitialWords())
        loadWordOfTheDay()
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
    }
}
