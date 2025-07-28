package com.example.sach.ui

import com.example.sach.data.Book
import com.example.sach.data.DSsach
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookViewModel : ViewModel() {

    private val _bookList = MutableStateFlow(DSsach())
    val bookList: StateFlow<List<Book>> = _bookList.asStateFlow()

    val favoriteBooks: StateFlow<List<Book>> = MutableStateFlow(
        DSsach().filter { it.favorite }
    )

    fun toggleFavorite(bookId: Int) {
        val updated = _bookList.value.map { book ->
            if (book.id == bookId) book.copy(favorite = !book.favorite) else book
        }
        _bookList.value = updated
    }

    fun getBookById(bookId: Int): Book? {
        return _bookList.value.find { it.id == bookId }
    }

    fun getFavoriteBooks(): List<Book> {
        return _bookList.value.filter { it.favorite }
    }
}