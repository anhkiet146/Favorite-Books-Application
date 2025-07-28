package com.example.sach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sach.ui.FavoriteBooksApp
import com.example.sach.ui.theme.SachTheme
import com.example.sach.ui.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SachTheme(dynamicColor = false) {
                Surface {
                    val bookViewModel: BookViewModel = viewModel()
                    FavoriteBooksApp()
                }
            }
        }
    }
}

