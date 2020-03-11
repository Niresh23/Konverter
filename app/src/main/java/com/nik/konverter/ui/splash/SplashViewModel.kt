package com.nik.konverter.ui.splash

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nik.konverter.model.Repository
import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.ui.base.BaseViewModel

class SplashViewModel(private val repository: Repository): BaseViewModel<String?, SplashViewState>() {

    init{
        viewStateLiveData.value = SplashViewState()
    }
    fun update() {
        repository.updateData().observeForever {
            when(it) {
                is ActionResult.Success<*> -> {
                    viewStateLiveData.value = SplashViewState("Данные обновлены")
                }
                is ActionResult.Error -> {
                    Log.e("SplashViewModel", it.error.message)
                    viewStateLiveData.value = SplashViewState("Ошибка обновления данных" )
                }
            }
        }
    }
}