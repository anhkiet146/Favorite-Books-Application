package com.example.sach

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel()
) {
    //navController: NavHostController,
//    val navController = rememberNavController()
    var searchQuery by remember { mutableStateOf("") }

    val filteredBooks = viewModel.books.filter {
        it.tensach.contains(searchQuery, ignoreCase = true) ||
                it.tacgia.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(

        ) {
            LogoIcon()
            Text(
                text = "Trang chủ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Tìm kiếm sách...") },
            modifier = Modifier.fillMaxWidth()
        )



        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredBooks) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.45f)
                        .clickable {
                            navController.navigate(NavigationItem.DETAIL.createRoute(book.id))
                        },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Image(
                            painter = painterResource(id = book.hinhanh),
                            contentDescription = null,
                            modifier = Modifier
                                .height(225.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(25.dp))
                        )

                        Spacer(modifier = Modifier.height(8.dp))


                        Text(
                            text = book.tensach,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                            )
                        Text(book.tacgia, style = MaterialTheme.typography.bodySmall)
                        Text("Năm XB: ${book.namxb}", style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.weight(1f))


                        IconButton(
                            onClick = {
                                viewModel.toggleFavorite(book.id)
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(
                                imageVector = if (book.favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Thêm vào mục yêu thích"
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.padding(start = 120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    navController.navigate(NavigationItem.GET_START.route)
                }

            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = {
                    navController.navigate(NavigationItem.SAVED.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Go to favorites",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = {
                    navController.navigate(NavigationItem.SAVED.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Go to favorites",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun LogoIcon(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(64.dp)
            .clip(MaterialTheme.shapes.small)
            .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
            .padding(15.dp)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(R.drawable.logo),

        contentDescription = null
    )
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewHomeScreen(){
//    val bookViewModel: BookViewModel = viewModel()
//    HomeScreen()
//}
