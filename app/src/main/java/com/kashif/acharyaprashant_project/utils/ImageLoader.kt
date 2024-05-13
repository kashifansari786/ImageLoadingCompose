package com.kashif.acharyaprashant_project.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by Mohammad Kashif Ansari on 12,May,2024
 */
class ImageLoader(private val context: Context) {

    private val memoryCache = MemoryCache()
    private val fileCache = DiskCache(context)
    //The executorService facilitates the asynchronous execution of tasks in a controlled manner.
    // It initializes a thread pool with a fixed size of 5 threads
    private val executorService: ExecutorService = Executors.newFixedThreadPool(5)


    fun loadImage(url: String, onBitmapLoaded: (Bitmap) -> Unit) {
//        val cachedBitmap = memoryCache.get(url)
//        Log.d("inside_cache","memorycache:- "+cachedBitmap)
//        if (cachedBitmap != null) {
//            onBitmapLoaded(cachedBitmap)
//        } else {
            executorService.submit {
                val bitmap = getBitmap(url)
                bitmap?.let {
                    memoryCache.put(url, it)
                    fileCache.put(url, it)
                    onBitmapLoaded(bitmap)
                }
            }
       // }
    }
private fun getBitmap(url: String): Bitmap? {
    // Check memory cache
    val cachedBitmap = memoryCache.get(url)
    if (cachedBitmap != null) {
        Log.d("inside_cache","memorycacheLoader:- "+cachedBitmap)
        return cachedBitmap
    }

    // Check disk cache
    val diskCachedBitmap = fileCache.get(url)
  //  Log.d("inside_cache","diskcache:- "+diskCachedBitmap)
    if (diskCachedBitmap != null) {
        memoryCache.put(url, diskCachedBitmap)
        Log.d("inside_cache","diskcache:- "+diskCachedBitmap)
        return diskCachedBitmap
    }
    // Download image from server
    val bitmap=downloadBitmap(url)
    bitmap?.let {
        memoryCache.put(url, it)
        fileCache.put(url, it)
    }
    Log.d("inside_cache","bitmap_down:- "+bitmap)
    return bitmap

//    val bitmap = downloadBitmap(url)
//    Log.d("inside_cache","downloadbitmap:- "+bitmap)
//    bitmap?.let {
//        //put url on both caches
//        memoryCache.put(url, it)
//        fileCache.put(url, it)
//    }
//    Log.d("inside_cache","return_bitmap:- "+bitmap)
//    return bitmap
}

    private fun downloadBitmap(url: String): Bitmap? {
        try {
            Log.d("inside_cache","dowmnload_image_url:- "+url)
            val imageUrl = URL(url)
            val conn = imageUrl.openConnection() as HttpURLConnection
            conn.connectTimeout = 30000
            conn.readTimeout = 30000
            conn.instanceFollowRedirects = true
            val inputStream: InputStream = conn.inputStream
            return BitmapFactory.decodeStream(inputStream)
//            Log.d("inside_cache","dowmnload_image:- "+BitmapFactory.decodeStream(inputStream))
//              BitmapFactory.decodeStream(inputStream)?.apply {
////                memoryCache.put(url, this)
////                fileCache.put(url, this)
//            }

        } catch (e: Throwable) {
            Log.d("inside_cache","dowmnload_excep:- "+e.localizedMessage)
            e.printStackTrace()
            return null
        }
    }

    fun clearCache() {
        memoryCache.clear()
        fileCache.clear()
    }
}

