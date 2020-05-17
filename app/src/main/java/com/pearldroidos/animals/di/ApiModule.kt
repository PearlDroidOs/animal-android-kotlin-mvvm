package com.pearldroidos.animals.di

import com.pearldroidos.animals.model.AnimalApi
import com.pearldroidos.animals.model.AnimalApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {
    private val BASE_API = "https://us-central1-apis-4674e.cloudfunctions.net"

    @Provides
    fun provideAnimalApi(): AnimalApi {
        //ConverterFactory means converting data from api to Animal and ApiKey objects by use gson transformation - from json to gson
        //RxJava2CallAdapterFactory means giving converting data into singleton that we declared on Single in AnimalApi
        //AdapterFactory: Get converting data from gson to object then it will do to be observable
        return  Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create()) //In another app: Make sure that you implement retrofit2:convert-gson in gradle
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //In another app: Make sure that you implement retrofit2:adapter-rxjava2 in gradle
            .build()
            .create(AnimalApi::class.java)
    }

    @Provides
    open fun provideAnimalApiService():AnimalApiService{
        return AnimalApiService()
    }
}

