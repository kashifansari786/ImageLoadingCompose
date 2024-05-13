//package com.kashif.acharyaprashant_project.screens
//
//import android.util.Log
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.size
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import com.kashif.acharyaprashant_project.R
//import com.kashif.acharyaprashant_project.navigations.Routes
//import com.kashif.acharyaprashant_project.viewmodels.MainViewModel
//import kotlinx.coroutines.delay
//
///**
// * Created by Mohammad Kashif Ansari on 10,May,2024
// */
//@Composable
//fun Splash(navHostController: NavHostController,viewModel: MainViewModel) {
//
//    //arrange the images in a centered column
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center) {
//            Image(painter = painterResource(id = R.drawable.splash),
//                contentDescription = "splash logo", modifier = Modifier.size(100.dp))
//    }
//
//    //We're using LaunchedEffect to handle navigation from the splash screen to the home screen after
//    // a 3-second delay. Within LaunchedEffect, we'll also perform tasks such as fetching data from
//    // a server or Firebase. Once these tasks are completed, we'll redirect to the home screen.
//    LaunchedEffect(Unit) {
//
//        delay(3000)
//        navHostController.navigate(Routes.Home.routes) {
//            popUpTo(navHostController.graph.startDestinationId)
//            launchSingleTop = true
//        }
//    }
//}