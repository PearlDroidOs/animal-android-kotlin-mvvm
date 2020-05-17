package com.pearldroidos.animals.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This retrofit implementation is extremely useful for us as developers because it gives us
 * without a lot of boilerplate code and a lot of issues and headaches
 *
 * Good points: giving network communication in an easy and adaptable with flexible way
 */
class AnimalApiService {

    private val BASE_API = "https://us-central1-apis-4674e.cloudfunctions.net"

    //ConverterFactory means converting data from api to Animal and ApiKey objects by use gson transformation - from json to gson
    //RxJava2CallAdapterFactory means giving converting data into singleton that we declared on Single in AnimalApi
    //AdapterFactory: Get converting data from gson to object then it will do to be observable
    private val api = Retrofit.Builder()
        .baseUrl(BASE_API)
        .addConverterFactory(GsonConverterFactory.create()) //In another app: Make sure that you implement retrofit2:convert-gson in gradle
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //In another app: Make sure that you implement retrofit2:adapter-rxjava2 in gradle
        .build()
        .create(AnimalApi::class.java)


    fun getApiKey(): Single<ApiKey>{
        return api.getApiKey()
    }

    fun getAnimals(key:String): Single<List<Animal>>{
        return api.getAnimal(key)
    }

}