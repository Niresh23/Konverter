package com.nik.konverter.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.nik.konverter.database.AppDatabase
import com.nik.konverter.database.Valuta
import com.nik.konverter.model.forms.ActionResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DatabaseRepository private constructor(val context: Context){

    companion object: SingletoneHolder<DatabaseRepository, Context> (::DatabaseRepository)

    private val database: AppDatabase

    init {
        val DATABASE_NAME = "valuta_db"
        database = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }



    fun getValutas() = MutableLiveData<ActionResult>().apply {
        database.valutaDao().getValuaList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : DisposableSingleObserver<List<Valuta>>() {
                override fun onSuccess(t: List<Valuta>) {
                    value = ActionResult.Success(t)
                }

                override fun onError(e: Throwable) {
                    value = ActionResult.Error(e)
                }
            })
    }

    fun deleteAll() = database.valutaDao().deleteAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun getById(id: String) = database.valutaDao().getValutaById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun insertValutas(valutas: List<Valuta>) = database.valutaDao().insert(valutas).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun updateValutas(valutas: List<Valuta>) = database.valutaDao().update(valutas).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}