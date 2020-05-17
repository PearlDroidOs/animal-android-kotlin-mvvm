package com.pearldroidos.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pearldroidos.animals.di.AppModule
import com.pearldroidos.animals.di.CONTEXT_APP
import com.pearldroidos.animals.di.DaggerViewModelComponent
import com.pearldroidos.animals.di.TypeOfContext
import com.pearldroidos.animals.model.Animal
import com.pearldroidos.animals.model.AnimalApiService
import com.pearldroidos.animals.model.ApiKey
import com.pearldroidos.animals.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * AndroidViewModel(application) or ViewModel()
 * Google does recommend that you do not use a context inside the view model because the activity context can be very transient
 * It can be destroyed and recreated then Google provided this to solve context problem while running app
 * Application is the main context to whole lifetime
 */
class ListViewModel(application: Application) : AndroidViewModel(application) {

    //'lazy' means basically that the system is not going to instantiate this live data variable unless
    //Use when it is needed --> if not it will not be needed
    val animals by lazy { MutableLiveData<List<Animal>>() } //Mutable mean 'can change'

    //Get load error from backend api
    val loadError by lazy { MutableLiveData<Boolean>() }

    //Loading variable is simply going to provide information that the system is processing in the background
    //Example: loading is true then view is simply going to display a spinning for a user
    val loading by lazy { MutableLiveData<Boolean>() }

    //Retrieve the data
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: AnimalApiService

    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var prefs: SharedPreferencesHelper

    private var invalidApiKey = false

    init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    fun refresh() {
        loading.value = true
        invalidApiKey = false

        //Check key in prefs
        val key = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    fun hardRefresh() {
        loading.value = true
        getKey()
    }

    /**
     * Call a disposable which means RxJava construct that basically takes the result of an observable
     */
    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {
                        if (key.key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            prefs.saveApiKey(key.key)   //Save key in prefs
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }

                })
        )
    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value = false
                        animals.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        //Happen when backend tells the key invalid
                        //Stop endless loop
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            loading.value = false
                            animals.value = null
                            loadError.value = true
                        }
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}