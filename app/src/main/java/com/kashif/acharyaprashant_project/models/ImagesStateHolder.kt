package com.kashif.acharyaprashant_project.models

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */
data class ImagesStateHolder(
    val isLoading:Boolean=false,
    val data:ArrayList<ImagesModelItem>?=null,
    val error:String=""
)
