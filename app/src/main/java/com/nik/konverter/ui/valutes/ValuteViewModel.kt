package com.nik.konverter.ui.valutes

import android.content.Context
import com.nik.konverter.database.Valuta
import com.nik.konverter.model.Repository
import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.ui.base.BaseViewModel

class ValuteViewModel: BaseViewModel<List<Valuta>?, ValuteViewState>() {
    private val repository = Repository()
    init {
        viewStateLiveData.value = ValuteViewState()
    }
    fun update(context: Context) {
        repository.getValutasResult(context).observeForever { t: ActionResult? ->
            t ?: return@observeForever
            when(t) {
                is ActionResult.Success<*> -> viewStateLiveData.value = ValuteViewState( valutas = t.data as List<Valuta>)
                is ActionResult.Error -> viewStateLiveData.value = ValuteViewState(error = t.error)
            }
        }
    }
}