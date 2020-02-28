package com.nik.konverter.ui.main

import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.model.forms.DataResult
import com.nik.konverter.ui.base.BaseViewState

class MainViewState(val valutas: DataResult? = null, error: Throwable? = null): BaseViewState<DataResult?>(valutas, error)