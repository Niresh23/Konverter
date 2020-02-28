package com.nik.konverter.ui.main

import android.content.Context
import com.nik.konverter.database.Valuta
import com.nik.konverter.model.Repository
import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.model.forms.DataResult
import com.nik.konverter.ui.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver

class MainViewModel: BaseViewModel <DataResult?, MainViewState>() {
    private val ID_USD = "R01235"
    private val ID_RUB = "R99999"

    val repository:Repository = Repository()

    fun getValСurs(context: Context) {
        repository.getValutaById(context, ID_RUB).subscribe(object : DisposableSingleObserver<Valuta>(){
            override fun onSuccess(t: Valuta) {

            }

            override fun onError(e: Throwable) {

            }
        })
    }
}