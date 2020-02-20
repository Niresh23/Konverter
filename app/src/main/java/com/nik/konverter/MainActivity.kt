package com.nik.konverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nik.konverter.model.forms.ValCurs
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    private val retrofit: Retrofit= Retrofit.Builder().baseUrl("http://www.cbr.ru/")
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofit.create(RestApi::class.java).getValute().retry(2).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe(object :
                    DisposableSingleObserver<ValCurs>() {
                    override fun onSuccess(t: ValCurs) {
                        Log.d("RDR", t.valutes[0].name)
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Error", e.message)
                    }
                })
    }

    interface RestApi {
        @GET("scripts/XML_daily.asp")
        fun getValute(): Single<ValCurs>
    }
}
