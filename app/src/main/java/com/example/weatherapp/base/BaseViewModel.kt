package com.example.weatherapp.view.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.weatherapp.networkClasses.UseCaseHandler
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(open val useCaseHandler: UseCaseHandler) : ViewModel() {
    val isLoading = ObservableBoolean()
    val compositeDisposable: CompositeDisposable
    init {
        this.compositeDisposable = CompositeDisposable()
    }
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }
}