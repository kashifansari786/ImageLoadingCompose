package com.kashif.acharyaprashant_project.utils

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */
sealed class Resource<T>(val data:T?=null,val message:String?=null) {

    class Loading<T>():Resource<T>()
    class Success<T>(data: T?):Resource<T>(data = data)
    class Error<T>(message:String?):Resource<T>(message=message)
}