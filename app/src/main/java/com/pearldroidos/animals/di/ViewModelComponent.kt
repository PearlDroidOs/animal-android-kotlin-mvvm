package com.pearldroidos.animals.di

import com.pearldroidos.animals.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * The function below can add on the ApiModule but it may confuse when other developers come to implement
 * Recommend to separate it
 */
@Singleton
@Component(modules = [ApiModule::class, PrefsModules::class, AppModule::class])
interface ViewModelComponent {

    fun inject(viewModel: ListViewModel)
}