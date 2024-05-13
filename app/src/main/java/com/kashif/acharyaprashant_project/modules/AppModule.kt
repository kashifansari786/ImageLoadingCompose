package com.kashif.acharyaprashant_project.modules

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.kashif.acharyaprashant_project.data.ImagesDataSource
import com.kashif.acharyaprashant_project.data.ImagesRepository
import com.kashif.acharyaprashant_project.network.ApiService
import com.kashif.acharyaprashant_project.utils.ConnectionStatus
import com.kashif.acharyaprashant_project.utils.DiskCache
import com.kashif.acharyaprashant_project.utils.ImageLoader
import com.kashif.acharyaprashant_project.utils.MemoryCache
import com.kashif.acharyaprashant_project.utils.currentConnectivityStatus
import com.kashif.acharyaprashant_project.utils.observeConnectivityAsFlow
import com.kashif.acharyaprashant_project.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Mohammad Kashif Ansari on 10,May,2024
 */
//tell dagger that this class is a singleton and it is a module
@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    //The @Provides annotation informs Dagger that a method provides a Retrofit dependency.
    // This dependency should be available throughout the entire application.
    @Provides
    @Singleton
    fun providesRetrofit(): ApiService {
        return Retrofit
            .Builder()
            .baseUrl("https://acharyaprashant.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideDiskCache(@ApplicationContext context: Context): DiskCache {
        return DiskCache(context)
    }

    @Provides
    @Singleton
    fun provideMemoryCache(): MemoryCache {
        return MemoryCache()
    }

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader(context)
    }


    @Provides
    fun provideImages(apiService: ApiService): ImagesDataSource {
        return ImagesDataSource(apiService)
    }
}