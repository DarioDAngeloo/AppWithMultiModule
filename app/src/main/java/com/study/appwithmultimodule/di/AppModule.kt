package com.study.appwithmultimodule.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.study.core.data.preferences.PreferencesImpl
import com.study.core.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun sharedPreferences(context: Application) : SharedPreferences {
        return context.getSharedPreferences("shared_preferences", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences) : Preferences {
        return PreferencesImpl(sharedPreferences)
    }



}