package com.pearldroidos.animals

import android.app.Application
import com.pearldroidos.animals.di.PrefsModules
import com.pearldroidos.animals.util.SharedPreferencesHelper

class PrefsModuleTest(val mockPrefs: SharedPreferencesHelper): PrefsModules() {

    override fun provideSharedPreference(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }

}