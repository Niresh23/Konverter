package com.nik.konverter.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nik.konverter.adapters.ValutaAdapter
import com.nik.konverter.database.Valuta
import com.nik.konverter.model.forms.ValCurs
import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.model.forms.Valute
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver

class Repository {

    val remouteRepository = RemouteRepository()

    fun updateData(context: Context) = MutableLiveData<ActionResult>().apply{
        remouteRepository.getValCurse().subscribe(object : DisposableSingleObserver<ValCurs>(){

            override fun onSuccess(valCurs: ValCurs) {
                DatabaseRepository.getInstance(context).deleteAll().subscribe(object : DisposableCompletableObserver() {

                    override fun onComplete() {
                        valCurs.valutes.add(0, Valute("R99999", "643", "RUB", "1", "Рубль", "1"))
                        insertValutas(context, ValutaAdapter().convert(valCurs)).subscribe(object : DisposableCompletableObserver() {

                            override fun onComplete() {
                                value = ActionResult.Success("Данные обновлены")
                            }

                            override fun onError(e: Throwable) {
                                value = ActionResult.Error(e)
                            }
                        })
                    }

                    override fun onError(e: Throwable) {
                        value = ActionResult.Error(e)
                    }
                })
            }
            override fun onError(e: Throwable) {
                value = ActionResult.Error(e)
            }
        })
    }
    fun insertValutas(context: Context, valutas: List<Valuta>) = DatabaseRepository.getInstance(context).insertValutas(valutas)
    fun getValutasResult(context: Context) = DatabaseRepository.getInstance(context).getValutas()
}