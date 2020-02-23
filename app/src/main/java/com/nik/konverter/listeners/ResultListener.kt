package com.nik.konverter.listeners

import com.nik.konverter.model.forms.ValCurs

interface ResultListener {
    fun onSuccess(result: ValCurs):ValCurs
    fun onError(e: Throwable): Throwable
}