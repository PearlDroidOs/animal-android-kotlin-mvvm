package com.pearldroidos.animals.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Define the information that we need to use and pass them
 * Get response from Backend and send them pass these methods as below 2 endpoint by Single
 */
interface AnimalApi {

    //We want Single endpoint
    @GET("getKey")
    fun getApiKey(): Single<ApiKey>

    //Receive key from getApiKey and use it to get Animal data
    //@FormUrlEncoded allow to use field: if you don't use it, a system will show '@Field parameters can only be used with form encoding.'
    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimal(@Field("key") key: String): Single<List<Animal>>

}