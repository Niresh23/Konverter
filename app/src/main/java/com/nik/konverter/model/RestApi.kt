package com.nik.konverter.model

import com.nik.konverter.model.forms.ValCurs
import io.reactivex.Single
import retrofit2.http.GET

interface RestApi {
    @GET("scripts/XML_daily.asp")
    fun getValute(): Single<ValCurs>
}