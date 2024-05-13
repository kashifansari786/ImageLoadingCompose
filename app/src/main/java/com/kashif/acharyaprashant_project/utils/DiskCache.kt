package com.kashif.acharyaprashant_project.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLEncoder
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */

class DiskCache(private val context: Context) {

    private val cacheDir: File = File(context.filesDir, "image_cache")

    init {
        //if cache dir is not exist create new
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    fun put(key: String, bitmap: Bitmap) {
        val file = getFile(key)
        try {
            val outputStream = FileOutputStream(file)
            //compress the bitmap to 80% in PNG format
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun get(key: String): Bitmap? {
        val file = getFile(key)
        if (file.exists()) {
            try {
                val inputStream = FileInputStream(file)
                return BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun getFile(key: String): File {
        val filename = key.hashCode().toString()
        return File(cacheDir, filename)
    }

    fun clear() {
        val files = cacheDir.listFiles()
        if (files != null) {
            for (file in files) {
                file.delete()
            }
        }
    }
}
