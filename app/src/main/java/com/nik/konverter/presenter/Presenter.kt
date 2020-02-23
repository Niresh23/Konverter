package com.nik.konverter.presenter

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nik.konverter.model.Model
import com.nik.konverter.model.forms.Valute
import com.nik.konverter.ui.main.MainActivity
import com.nik.konverter.ui.valutes.ValuteActivity

class Presenter {

    fun getValCurse() = Model.getValCurse()

}

