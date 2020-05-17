package com.pearldroidos.animals.model

import com.pearldroidos.animals.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

/**
 * This retrofit implementation is extremely useful for us as developers because it gives us
 * without a lot of boilerplate code and a lot of issues and headaches
 *
 * Good points: giving network communication in an easy and adaptable with flexible way
 */
class AnimalApiService {

    @Inject
    lateinit var api:AnimalApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey>{
        return api.getApiKey()
    }

    fun getAnimals(key:String): Single<List<Animal>>{
        return api.getAnimal(key)
    }

}