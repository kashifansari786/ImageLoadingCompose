package com.kashif.acharyaprashant_project.utils

import java.util.Collections;
import java.util.LinkedHashMap;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */


class MemoryCache {
    private val TAG = "MemoryCache"
    private val cache: MutableMap<String, Bitmap> = Collections.synchronizedMap(
        LinkedHashMap<String, Bitmap>(10, 1.5f, true)
    ) // Last argument true for LRU ordering
    private var size: Long = 0 // current allocated size
    private var limit: Long = 1000000 // max memory in bytes

    init {
        // use 25% of available heap size
        setLimit(Runtime.getRuntime().maxMemory() / 4)
    }

    fun setLimit(newLimit: Long) {
        limit = newLimit
        Log.i(TAG, "MemoryCache will use up to ${limit / 1024.0 / 1024.0}MB")
    }

    fun get(id: String): Bitmap? {
        try {
            if (!cache.containsKey(id))
                return null
            // NullPointerException sometimes happen h
            return cache[id]
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            return null
        }
    }

    fun put(id: String, bitmap: Bitmap) {
        try {
            if (cache.containsKey(id))
                size -= getSizeInBytes(cache[id]!!)
            cache[id] = bitmap
            size += getSizeInBytes(bitmap)
            checkSize()
        } catch (th: Throwable) {
            th.printStackTrace()
        }
    }

    private fun checkSize() {
        Log.i(TAG, "cache size=$size length=${cache.size} limit=${limit} compare=${size > limit}")
        if (size > limit) {
            val iter: MutableIterator<MutableMap.MutableEntry<String, Bitmap>> = cache.entries.iterator() // least recently accessed item will be the first one iterated
            while (iter.hasNext()) {
                val entry: MutableMap.MutableEntry<String, Bitmap> = iter.next()
                size -= getSizeInBytes(entry.value)
                iter.remove()
                if (size <= limit)
                    break
            }
            Log.i(TAG, "Clean cache. New size ${cache.size}")
        }
    }

    fun clear() {
        try {
            // NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78
            cache.clear()
            size = 0
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        }
    }

    private fun getSizeInBytes(bitmap: Bitmap?): Long {
        if (bitmap == null)
            return 0
        return bitmap.rowBytes * bitmap.height.toLong()
    }
}