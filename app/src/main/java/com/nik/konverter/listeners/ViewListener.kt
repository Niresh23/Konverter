package com.nik.konverter.listeners

import com.nik.konverter.model.forms.Valute

interface ViewListener {

    fun setFirstCurs(curs: Valute)
    fun setSecondCurs(curs: Valute)
    fun showError(e: Throwable)
}