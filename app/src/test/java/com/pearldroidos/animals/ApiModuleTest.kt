package com.pearldroidos.animals

import com.pearldroidos.animals.di.ApiModule
import com.pearldroidos.animals.model.AnimalApiService

class ApiModuleTest(val mockService: AnimalApiService): ApiModule(){

    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }
}