package com.nik.konverter.model

import androidx.lifecycle.MutableLiveData
import com.nik.konverter.model.forms.ValCurs
import com.nik.konverter.ui.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class RemouteRepository {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://www.cbr.ru/")
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getValCurse() = MutableLiveData<ValuteResult>().apply{
        retrofit.create(MainActivity.RestApi::class.java).getValute().retry(2)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread()).subscribe(object :
                DisposableSingleObserver<ValCurs>() {
                override fun onSuccess(t: ValCurs) {
                    value = ValuteResult.Success(t)
                }

                override fun onError(e: Throwable) {
                    value = ValuteResult.Error(e)
                }
            })
    }
}