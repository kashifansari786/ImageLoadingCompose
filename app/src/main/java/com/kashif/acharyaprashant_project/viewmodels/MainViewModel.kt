package com.kashif.acharyaprashant_project.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashif.acharyaprashant_project.data.ImagesRepository
import com.kashif.acharyaprashant_project.models.ImagesModelItem
import com.kashif.acharyaprashant_project.models.ImagesStateHolder
import com.kashif.acharyaprashant_project.utils.ImageLoader
import com.kashif.acharyaprashant_project.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */

//giving information to dagger that this class is a viewmodel with below annotation
//Inject constructor for applying dependencies
@HiltViewModel
class MainViewModel @Inject constructor(private val imagesRepository: ImagesRepository,
                                        private val imageLoader: ImageLoader
)  : ViewModel(){
     val imageList= mutableStateOf(ImagesStateHolder())
    val imageItemDetails= mutableStateOf<ImagesModelItem?>(null)
    val imageItemBitmap= mutableStateOf<Bitmap?>(null)
    val isInternetAvailable= mutableStateOf(false)
    private var currentJob: Job? = null
    //When a ViewModel is loaded, its init method is the first to be called.
    init {
        imageList.value= ImagesStateHolder(isLoading = true)
    }

    //internet is present or not
    fun updateInternetConnectivity(isConnected: Boolean) {
       isInternetAvailable.value = isConnected
        if(isInternetAvailable.value)
            getImagesList()
        else{
            imageList.value= ImagesStateHolder(isLoading = false)
            imageList.value= ImagesStateHolder(error = "No internet connection.")
        }
    }
    fun addImageDetailsData(image: ImagesModelItem, bitmapState: Bitmap?){
        imageItemDetails.value=image
        imageItemBitmap.value=bitmapState
    }
    //we have called viewModelScope which is a coroutine function and we have using Dispatched.IO methods
    fun getImagesList(){
        currentJob?.cancel()
        currentJob=viewModelScope.launch {

            val result=imagesRepository.getImageList()
            when(result){
                is Resource.Loading->{}
                is Resource.Success->{
                    imageList.value= ImagesStateHolder(data = result.data, isLoading = false)
                }
                is Resource.Error->{
                    imageList.value= ImagesStateHolder(error = result.message.toString(), isLoading = false)
                }
                else->{}
            }
        }
    }

    fun loadImage(url: String, onBitmapLoaded: (Bitmap) -> Unit) {
        //Pass the URL to the image loader, which will first check if the bitmap is present in the memory or disk cache.
        // If not found, it will download the image from the server
        imageLoader.loadImage(url) { bitmap ->
            Log.d("inside_cache","viewmodel_bitmap:- "+bitmap)
            onBitmapLoaded(bitmap)
        }
    }
}