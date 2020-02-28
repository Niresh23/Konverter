package com.nik.konverter.ui.splash

import android.content.Context
import android.util.Log
import com.nik.konverter.model.Repository
import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.ui.base.BaseViewModel

class SplashViewModel: BaseViewModel<String?, SplashViewState>(){
    val repository: Repository = Repository()

    init{
        viewStateLiveData.value = SplashViewState()
    }
    fun update(context: Context) {
        repository.updateData(context).observeForever {
            Log.d("SplashViewModel", "observeForever()")

            when(it) {
                is ActionResult.Success<*> -> {
                    viewStateLiveData.value = SplashViewState("Данные обновлены")
                }
                is ActionResult.Error -> {
                    viewStateLiveData.value = SplashViewState("Ошибка: " + it.error.message)
                }
            }
    }
    }
}