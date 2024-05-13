package com.kashif.acharyaprashant_project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kashif.acharyaprashant_project.navigations.Routes
import com.kashif.acharyaprashant_project.viewmodels.MainViewModel

/**
 * Created by Mohammad Kashif Ansari on 12,May,2024
 */

@Composable
fun HomeDetails(navHostController: NavHostController, viewModel: MainViewModel){

    val result=viewModel.imageItemDetails.value
    val bitmap=viewModel.imageItemBitmap.value

    Box(modifier = Modifier
        .padding(5.dp)
        .fillMaxSize(), contentAlignment = Alignment.BottomStart) {
        bitmap?.let {
            it?.asImageBitmap()?.let { it1 ->
                Image(
                    bitmap = it1,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        result?.let {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Yellow, Color.Green
                        )
                    )
                )){
                Column (modifier = Modifier.padding(3.dp)){
                    Text(text = it.title, style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Publish at:- ${it.publishedAt}", style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Publish at:- ${it.publishedBy}", style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color.Gray)
                }

            }

        }

    }
}