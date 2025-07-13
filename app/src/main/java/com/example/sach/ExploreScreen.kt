package com.example.sach

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun ExploreScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel()
) {
    var keyword by remember { mutableStateOf("") }
    var ketQua by remember { mutableStateOf<List<Book>>(emptyList()) }

    val focusManager = LocalFocusManager.current
    val allBooks = DSsach()

    // Hàm bỏ dấu tiếng Việt
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

    // Hàm tìm kiếm khi bấm nút hoặc Enter
    fun timKiemSach() {
        val key = keyword.normalize()
        ketQua = if (key.isBlank()) emptyList()
        else allBooks.filter {
            it.tensach.normalize().contains(key) ||
                    it.tacgia.normalize().contains(key)
        }
        focusManager.clearFocus()
    }

    // Gợi ý realtime khi đang gõ
    val suggestions = remember(keyword) {
        if (keyword.isBlank()) emptyList()
        else {
            val key = keyword.normalize()
            allBooks.filter {
                it.tensach.normalize().contains(key) ||
                        it.tacgia.normalize().contains(key)
            }.take(5)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        OutlinedTextField(
            value = keyword,
            onValueChange = { keyword = it },
            label = { Text("Nhập tên sách hoặc tác giả") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { timKiemSach() }
            )
        )

        // Gợi ý bên dưới ô nhập
        if (suggestions.isNotEmpty()) {
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
                                timKiemSach()
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

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { timKiemSach() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Tìm")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Kết quả chính thức sau khi tìm
        if (ketQua.isNotEmpty()) {
            LazyColumn {
                items(ketQua) { sach ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate(NavigationItem.DETAIL.createRoute(sach.id))
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
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
