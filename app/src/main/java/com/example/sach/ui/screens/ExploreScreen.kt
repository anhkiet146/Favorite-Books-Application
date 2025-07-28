package com.example.sach.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sach.data.Book
import com.example.sach.data.DSsach
import com.example.sach.ui.NavigationItem
import com.example.sach.ui.BookViewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.ui.platform.LocalLayoutDirection



@Composable
fun ExploreScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel()
) {
    var keyword by rememberSaveable  { mutableStateOf("") }
    var ketQua by rememberSaveable  { mutableStateOf<List<Book>>(emptyList()) }
    val layoutDirection = LocalLayoutDirection.current
    val focusManager = LocalFocusManager.current
    val allBooks = DSsach()
    var isSearchPerformed by rememberSaveable { mutableStateOf(false) }

    fun String.normalize(): String {
        return this.lowercase()
            .replace(Regex("[àáạảãâầấậẩẫăằắặẳẵ]"), "a")
            .replace(Regex("[èéẹẻẽêềếệểễ]"), "e")
            .replace(Regex("[ìíịỉĩ]"), "i")
            .replace(Regex("[òóọỏõôồốộổỗơờớợởỡ]"), "o")
            .replace(Regex("[ùúụủũưừứựửữ]"), "u")
            .replace(Regex("[ỳýỵỷỹ]"), "y")
            .replace(Regex("đ"), "d")
    }

    fun timKiemSach() {
        val keywords = keyword.normalize().split("\\s+".toRegex())
        ketQua = if (keywords.isEmpty()) emptyList()
        else allBooks.filter { book ->
            val title = book.tensach.normalize()
            val author = book.tacgia.normalize()
            keywords.all { word ->
                title.contains(word) || author.contains(word)
            }
        }
        isSearchPerformed = true
        focusManager.clearFocus()
    }

    val suggestions = remember(keyword) {
        val normalized = keyword.normalize()
        if (normalized.length < 2) return@remember emptyList()

        val keywords = normalized.split("\\s+".toRegex())
        allBooks.filter { book ->
            val title = book.tensach.normalize()
            val author = book.tacgia.normalize()
            keywords.all { word ->
                title.contains(word) || author.contains(word)
            }
        }.take(5)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),
    ) {
        TopAppBar(
            onBackClick = { navController.popBackStack() }
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = keyword,
                    onValueChange = {
                        keyword = it
                        isSearchPerformed = false
                    },
                    placeholder = { Text("Nhập tên sách hoặc tác giả") },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(topStart = 25.dp, bottomStart = 25.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { timKiemSach() })
                )


                Button(
                    onClick = { timKiemSach() },
                    modifier = Modifier
                        .height(56.dp),
                    shape = RoundedCornerShape(topEnd = 25.dp, bottomEnd = 25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text("Tìm")
                }
            }
        }

        if (suggestions.isNotEmpty() && !isSearchPerformed) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
            ) {
                items(suggestions) { sach ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                keyword = sach.tensach
                                timKiemSach()  // thực hiện tìm kiếm ngay
                            }
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(sach.tensach)
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        if (ketQua.isNotEmpty()) {
            LazyColumn {
                items(ketQua) { sach ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .padding(start = 5.dp, end = 5.dp)
                            .clickable {
                                navController.navigate(NavigationItem.Detail.createRoute(sach.id))
                            },
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(modifier = Modifier.padding(8.dp)) {
                            Image(
                                painter = painterResource(id = sach.hinhanh),
                                contentDescription = sach.tensach,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(sach.tensach, style = MaterialTheme.typography.titleMedium)
                                Text("Tác giả: ${sach.tacgia}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        } else if (keyword.isNotBlank() && suggestions.isEmpty()) {
            Text("Không tìm thấy sách nào.", color = Color.Red)
        }
    }
}

