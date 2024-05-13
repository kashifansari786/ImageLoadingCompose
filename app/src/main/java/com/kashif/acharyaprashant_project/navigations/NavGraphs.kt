package com.kashif.acharyaprashant_project.navigations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kashif.acharyaprashant_project.models.ImagesModelItem
import com.kashif.acharyaprashant_project.screens.Home
import com.kashif.acharyaprashant_project.screens.HomeDetails
import com.kashif.acharyaprashant_project.viewmodels.MainViewModel

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */

@Composable
fun NavGraphs(navController: NavHostController,viewModel: MainViewModel){

    //navhost will provide navigation of composable files
    NavHost(navController = navController, startDestination = Routes.Home.routes){
//        composable(Routes.Splash.routes){
//            Splash(navController,viewModel)
//        }
        composable(Routes.Home.routes){
            Home(navController,viewModel)
        }

        composable(Routes.HomeDetails.routes, enterTransition = {
            fadeIn(animationSpec = tween(2000))
        }){
            //getting ImagesModelItem object from backstack entry and pass to homeDetails class this technique is not effective
            // as we are using same viewmodel so we will put data to videmodel and get data from them
            val result=navController.previousBackStackEntry?.savedStateHandle?.get<ImagesModelItem>("data")
            HomeDetails(navController,viewModel)
        }
    }
}