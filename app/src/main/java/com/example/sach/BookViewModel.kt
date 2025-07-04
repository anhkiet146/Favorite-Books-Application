<<<<<<< HEAD
package com.example.sach

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {
    private val _books = mutableStateListOf<Book>().apply { addAll(DSsach()) }
    val books: List<Book> = _books

    // Xóa yêu thích
    fun toggleFavorite(bookId: Int) {
        val index = _books.indexOfFirst { it.id == bookId }
        if (index != -1) {
            val current = _books[index]
            _books[index] = current.copy(favorite = !current.favorite)
        }
    }

    // Yêu thích
    fun getFavoriteBooks(): List<Book> {
        return _books.filter { it.favorite }
    }
}
=======
package com.example.sach

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {
    private val _books = mutableStateListOf<Book>().apply { addAll(DSsach()) }
    val books: List<Book> = _books

    // Xóa yêu thích
    fun toggleFavorite(bookId: Int) {
        val index = _books.indexOfFirst { it.id == bookId }
        if (index != -1) {
            val current = _books[index]
            _books[index] = current.copy(favorite = !current.favorite)
        }
    }

    // Yêu thích
    fun getFavoriteBooks(): List<Book> {
        return _books.filter { it.favorite }
    }
}
>>>>>>> thien
