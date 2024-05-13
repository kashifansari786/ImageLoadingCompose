package com.kashif.acharyaprashant_project

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kashif.acharyaprashant_project.navigations.NavGraphs
import com.kashif.acharyaprashant_project.ui.theme.ImageLoadingComposeTheme
import com.kashif.acharyaprashant_project.utils.ConnectionStatus
import com.kashif.acharyaprashant_project.utils.currentConnectivityStatus
import com.kashif.acharyaprashant_project.utils.observeConnectivityAsFlow
import com.kashif.acharyaprashant_project.viewmodels.MainViewModel
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Singleton

//giving info to dagger that this is a entry point for application
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageLoadingComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //navHostController object
                   val navHostController:NavHostController= rememberNavController()
                    //hiltViewModel object
                    val viewModel:MainViewModel= hiltViewModel()
                    viewModel.updateInternetConnectivity(checkConnectivityStatus())

                    //passed all object to nagGraphs for navigation
                    NavGraphs(navController = navHostController, viewModel = viewModel)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun checkConnectivityStatus():Boolean{
        val connection by connectivityStatus()
        val isConnected=connection === ConnectionStatus.Available
        return isConnected

    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun connectivityStatus(): State<ConnectionStatus> {
        val context= LocalContext.current
        return produceState(initialValue = context.currentConnectivityStatus) {
            context.observeConnectivityAsFlow().collect{
                value=it
            }
        }
    }

}