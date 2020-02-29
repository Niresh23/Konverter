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
        DatabaseRepository.getInstance(context).deleteAll().subscribe(object : DisposableCompletableObserver(){
            override fun onComplete() {
                Log.d("Repository", "deleteAll().onComplete()")
                remouteRepository.getValCurse().subscribe(object : DisposableSingleObserver<ValCurs>() {
                    override fun onSuccess(valCurs: ValCurs) {
                        Log.d("Repository", "getValCurse().onSuccess()" + valCurs.valutes[0].name)
                        valCurs.valutes.add(0, Valute("R99999", "643", "RUB", "1", "Рубль", "1"))
                        Log.d("Repository", "getValCurse().onSuccess()" + valCurs.valutes[3].name)
                        insertValutas(context, ValutaAdapter().convert(valCurs)).subscribe(object : DisposableCompletableObserver() {
                            override fun onComplete() {
                                value = ActionResult.Success("Данные обновлены")
                                Log.d("Repository", "insertValutas().onComplete()")
                            }
                            override fun onError(e: Throwable) {
                                Log.d("Repository", "insertValutas().onError")
                                value = ActionResult.Error(e)
                            }
                        })
                    }
                    override fun onError(e: Throwable) {
                        Log.d("Repository", "getValCurse().onError()")
                        value = ActionResult.Error(e)
                    }
                })
            }
            override fun onError(e: Throwable) {
                Log.d("Repository", "deleteAll().onError()")
                value = ActionResult.Error(e)
            }
        })
    }

    fun getValutaById(context: Context, leftId: String) = DatabaseRepository.getInstance(context).getById(leftId)

    fun insertValutas(context: Context, valutas: List<Valuta>) = DatabaseRepository.getInstance(context).insertValutas(valutas)

    fun updateValutas(context: Context, valutas: List<Valuta>) = DatabaseRepository.getInstance(context).updateValutas(valutas)

    fun getValutasResult(context: Context) = DatabaseRepository.getInstance(context).getValutas()
}