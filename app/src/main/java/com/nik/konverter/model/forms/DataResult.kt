package com.nik.konverter.model.forms

data class DataResult(
    var idLeft: String? = null,
    var idRight: String? = null,
    var valueLeft: Float? = null,
    var valueRight: Float? = null,
    var resultLeft: String? = null,
    var resultRight: String? = null,
    var charCodeLeft: String? = null,
    var charCodeRight: String? = null)