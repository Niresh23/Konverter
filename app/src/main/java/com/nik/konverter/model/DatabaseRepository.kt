package com.nik.konverter.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.nik.konverter.database.AppDatabase
import com.nik.konverter.database.Valuta
import com.nik.konverter.database.ValutaDao
import com.nik.konverter.model.forms.ActionResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.simpleframework.xml.core.Validate

class DatabaseRepository private constructor(private val valutaDao: ValutaDao) {

    companion object {
        //Singleton instantiation
        @Volatile var instance:DatabaseRepository? = null
        fun getInstance(valutaDao: ValutaDao): DatabaseRepository {
            return instance ?: synchronized(this) {
                instance ?: DatabaseRepository(valutaDao).also { instance = it }
            }
        }
    }

    fun getValutas() = MutableLiveData<ActionResult>().apply {
        valutaDao.getValuaList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : DisposableSingleObserver<List<Valuta>>() {
                override fun onSuccess(t: List<Valuta>) {
                    value = ActionResult.Success(t)
                }

                override fun onError(e: Throwable) {
                    value = ActionResult.Error(e)
                }
            })
    }

    fun deleteAll() = valutaDao.deleteAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun getById(id: String) = valutaDao.getValutaById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun insertValutas(valutas: List<Valuta>) = valutaDao.insert(valutas).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun updateValutas(valutas: List<Valuta>) = valutaDao.update(valutas).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}