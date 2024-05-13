package com.kashif.acharyaprashant_project.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kashif.acharyaprashant_project.viewmodels.MainViewModel
import com.kashif.acharyaprashant_project.R
import com.kashif.acharyaprashant_project.navigations.Routes

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navHostController: NavHostController,viewModel: MainViewModel){
    //get imagelist data from viewmodel
     val result=viewModel.imageList.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Acharya Prashant",
                        style = MaterialTheme.typography.bodyMedium,
                        color = White,
                        fontSize = 14.sp)
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondary)
            )
        }, content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //if result is in loading state then it will show progressbar
                if(result.isLoading){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator()
                    }
                }
                //if result has an error then it will show in text
                if(result.error.isNotBlank()){
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp), contentAlignment = Alignment.Center){
                        Text(text = result.error)
                    }
                }
                //if result is not null
                result.data?.let {
                    //lazy verticle grid is used for display images in grid format
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        content = {
                            items(result.data?.size!!) { index ->
                               //Show a progress bar while the image is loading.
                                var loading by remember { mutableStateOf(true) }
                                //bitmap which will be genrated from the viewmodel
                                var bitmapState by remember { mutableStateOf<Bitmap?>(null) }
                                //element get the thumbnail class object
                                val element=result.data.get(index).thumbnail
                                //Generating an image URL with specific instructions
                                val imageURL = element.domain + "/" + element.basePath + "/0/" + element.key

                                // Asynchronously load image
                                LaunchedEffect(imageURL) {
                                    viewModel.loadImage(imageURL) { loadedBitmap ->
                                        //loadedBitmap is assign to local bitmap
                                        bitmapState = loadedBitmap
                                        //The image has finished loading and will now be displayed in the ImageView,
                                        // so the loading state will be set to false
                                        loading = false
                                    }
                                }
                                Log.d("inside_cache","home_bitmap:- "+bitmapState)
                                //I want to create a layout where there's a rectangular box containing an image at the top
                                // and the title of the image below it. To achieve this, I'm using a column layout
                                Column (modifier = Modifier
                                    .padding(4.dp)
                                    .height(150.dp)
                                    .background(Color.White, shape = RoundedCornerShape(5.dp)), //The background will be white, and the box will have rounded corners
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center){
                                    //box layour for holding image layout
                                    Box(modifier  = Modifier
                                        .size(100.dp)
                                        .clickable {
                                            //put imagesModelItem class to backstackentry and later we will get that object in homeDetails screen this is
                                            // one approch which is not very effective and also if we popStack this class then ImageModel object will be
                                            // destroy and homeDetails screen will get null

//                                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
//                                                key="data",
//                                                value = result.data.get(index)
//                                            )

                                            //set imagesModelItem model to viewmodel class and get from viewmodel
                                            viewModel.addImageDetailsData(result.data.get(index),bitmapState)
                                            navHostController.navigate(Routes.HomeDetails.routes)
                                        }) {
                                        bitmapState?.asImageBitmap()?.let { it1 ->
                                            Log.d("inside_cache","home_bitmap_state:- "+it1)
                                            Image(
                                                bitmap = it1,
                                                contentDescription = "Image from URL: $imageURL",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                        //If the loading state is true, a loading indicator will be shown. Otherwise,
                                        // an image placeholder will be displayed below the loader
                                        if (loading) {
                                            Box(modifier  = Modifier
                                                .size(100.dp)) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.no_image_placeholder),
                                                        contentDescription = "placeholder",
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.fillMaxSize()
                                                    )


                                                LinearProgressIndicator(
                                                    color = Color.Cyan,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(10.dp)
                                                        .padding(4.dp)
                                                        .align(Alignment.Center)
                                                )
                                            }

                                        }
                                    }
//                                   //title text
                                    Text(text = result.data.get(index).title,
                                        fontSize = 10.sp,
                                        letterSpacing = 0.2.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,  //elipside from the end
                                        modifier = Modifier.padding(2.dp))
                                }

                            }
                        }
                    )
                }

            }

        })

}