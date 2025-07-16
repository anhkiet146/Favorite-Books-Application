package com.example.sach.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.sach.model.Book
import com.example.sach.data.DSsach
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sach.data.FavoritePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val favoritePrefs = FavoritePreferences(application)

    private val _favoriteBookIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteBookIds: StateFlow<Set<String>> = _favoriteBookIds

    private val _books = mutableStateListOf<Book>().apply { addAll(DSsach()) }
    val books: List<Book> = _books

    init {
        viewModelScope.launch {
            favoritePrefs.favoriteBookIds.collect {
                _favoriteBookIds.value = it
            }
        }
    }

    fun toggleFavorite(bookId: Int) {
        val index = _books.indexOfFirst { it.id == bookId }
        if (index != -1) {
            val current = _books[index]
            _books[index] = current.copy(favorite = !_favoriteBookIds.value.contains(bookId.toString()))
        }
        viewModelScope.launch {
            favoritePrefs.toggleFavorite(bookId.toString())
        }
    }

    fun getFavoriteBooks(): List<Book> {
        return _books.filter { _favoriteBookIds.value.contains(it.id.toString()) }
    }

    fun getBookById(bookId: Int): Book? {
        return _books.find { it.id == bookId }
    }

    fun getAllBooks(): List<Book> = _books
}