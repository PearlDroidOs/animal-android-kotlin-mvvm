package com.pearldroidos.animals.di

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.pearldroidos.animals.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
class PrefsModules{

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    fun provideSharedPreference(app:Application):SharedPreferencesHelper{
        return SharedPreferencesHelper(app)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPreference(activity: AppCompatActivity):SharedPreferencesHelper{
        return SharedPreferencesHelper(activity)
    }
}

const val CONTEXT_APP = "Application context"
const val CONTEXT_ACTIVITY = "Activity context"


@Qualifier
annotation class TypeOfContext(val type: String)