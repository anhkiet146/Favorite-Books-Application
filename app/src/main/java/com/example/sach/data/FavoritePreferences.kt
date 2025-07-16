package com.example.sach.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "favorites")

class FavoritePreferences(private val context: Context) {

    companion object {
        val FAVORITE_BOOK_IDS = stringSetPreferencesKey("favorite_book_ids")
    }

    val favoriteBookIds: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVORITE_BOOK_IDS] ?: emptySet()
        }

    suspend fun toggleFavorite(bookId: String) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITE_BOOK_IDS] ?: emptySet()
            preferences[FAVORITE_BOOK_IDS] = if (bookId in currentFavorites) {
                currentFavorites - bookId
            } else {
                currentFavorites + bookId
            }
        }
    }
}