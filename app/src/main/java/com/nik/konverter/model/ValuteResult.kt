package com.nik.konverter.model

sealed class ValuteResult {
    data class Success<out T>(val data: T): ValuteResult()
    data class Error(val error: Throwable): ValuteResult()
}