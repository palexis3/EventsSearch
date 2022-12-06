package com.example.eventsearch

import android.app.Application
import android.content.Context
import com.example.eventsearch.helper.WifiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideWifiService(@ApplicationContext context: Context): WifiService {
        val wifiService = WifiService.instance
        wifiService.initialize(context)

        return wifiService
    }
}
