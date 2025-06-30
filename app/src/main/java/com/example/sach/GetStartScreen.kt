package com.example.sach

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.layout.ContentScale

@Composable
fun GetStartScreen(navController: NavHostController) {
    //navController: NavHostController
    //val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.nen),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                modifier = Modifier.size(100.dp),
                contentDescription = "logo Thư viện",
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 35.sp,
                            color = Color.Gray,

                            )
                    ) {
                        append("THƯ VIỆN \n\n\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 50.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("       SÁCH")
                    }
                }
            )

            Spacer(modifier = Modifier.height(90.dp))

            Button(onClick = {
                navController.navigate(NavigationItem.GET_START.route)
            }) {
                Text("Get Started")
            }
        }
    }
}
