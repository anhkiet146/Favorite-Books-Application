package com.example.sach

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun BookDetails(
    navController: NavHostController,
    bookId: Int?,
    viewModel: BookViewModel
) {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
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
        val book = viewModel.getBookById(bookId ?: -1)

        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                onBackClick = {
                    navController.popBackStack() // QUAY VỀ TRANG TRƯỚC (HomeScreen)
                },
                onNotificationClick = { /* làm gì đó */ }
            )

            if (book != null) {
                DetailContent(book)
            } else {
                Text("Không tìm thấy sách", modifier = Modifier.padding(16.dp))
            }
        }
    }

}


@Composable
fun TopAppBar(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(elevation = 4.dp, ambientColor = Color.Black, spotColor = Color.Black),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.size(48.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = onNotificationClick, modifier = Modifier.size(48.dp)) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Nofi",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun DetailContent(book: Book) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(book.hinhanh),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(400.dp)
                    .width(300.dp)
                    .padding(top = 40.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            )

            Text(
                text = book.tensach,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(top = 30.dp),
                textAlign = TextAlign.Center,
                lineHeight = 36.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tác giả: ${book.tacgia}",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "Năm xuất bản: ${book.namxb}",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.3f)
        )
        Text(
            text = "Mô tả",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 16.dp)
        )
        Text(
            text = book.mota,
            fontSize = 14.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify
        )
    }
}



