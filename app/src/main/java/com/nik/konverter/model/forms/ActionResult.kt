package com.nik.konverter.model.forms

sealed class ActionResult {
    data class Success<out T>(val data: T): ActionResult()
    data class Error(val error: Throwable): ActionResult()
}