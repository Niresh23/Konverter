package com.nik.konverter.model

import androidx.lifecycle.MutableLiveData
import com.nik.konverter.adapters.ValutaAdapter
import com.nik.konverter.database.Valuta
import com.nik.konverter.model.forms.ValCurs
import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.model.forms.Valute
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver

class Repository constructor(val databaseRepository: DatabaseRepository) {

    private val remouteRepository = RemoteRepository()
    private val valuteRUB = Valute("R99999", "643", "RUB", "1", "Рубль", "1")
    private val MESSAGE_SUCCESS = "Данные обновлены"

    fun updateData() = MutableLiveData<ActionResult>().apply{
        //Запрашивает данные из сети
        remouteRepository.getValCurse().subscribe(object : DisposableSingleObserver<ValCurs>(){

            override fun onSuccess(valCurs: ValCurs) {
                //В случае успешного получения данных очищает БД
                databaseRepository.deleteAll().subscribe(object : DisposableCompletableObserver() {

                    override fun onComplete() {
                        //После успешной очистки БД, добавляет валюту Рубль в начало списка, так как его нет в полученных данных
                        valCurs.valutes.add(0, valuteRUB)
                        insertValutas(ValutaAdapter().convert(valCurs)).subscribe(object : DisposableCompletableObserver() {

                            override fun onComplete() {
                                //После успешного добавления записей в БД передает сообщение об успешном обновлении данных
                                value = ActionResult.Success(MESSAGE_SUCCESS)
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

    fun insertValutas(valutas: List<Valuta>) = databaseRepository.insertValutas(valutas)
    fun getValutasResult() = databaseRepository.getValutas()
}