package com.example.sach

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.*
import com.example.sach.navigation.NavigationItem
import androidx.compose.material3.MaterialTheme



@Composable
fun GetStartScreen(
    navController: NavHostController,
    onSplashFinished: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "splash_alpha"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(1000)
        onSplashFinished() // Gọi để đánh dấu splash đã chạy
        navController.navigate(NavigationItem.HOME.route) {
            popUpTo(NavigationItem.SPLASH.route) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_removebg_preview),
            contentDescription = "Logo thư viện",
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha)
        )
    }

}
