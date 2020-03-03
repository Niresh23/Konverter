package com.nik.konverter.ui.valutes

import com.nik.konverter.database.Valuta
import com.nik.konverter.ui.base.BaseViewState

class ValutasViewState(val valutas: List<Valuta>? = null, error: Throwable? = null): BaseViewState<List<Valuta>?>(valutas, error)