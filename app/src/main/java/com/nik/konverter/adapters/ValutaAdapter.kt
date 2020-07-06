package com.nik.konverter.adapters

import com.nik.konverter.database.Valuta
import com.nik.konverter.model.forms.ValCurs
import com.nik.konverter.model.forms.Valute

class ValutaAdapter {
    fun convert(valCurs: ValCurs): List<Valuta> {
        val list = mutableListOf<Valuta>()

        for(item in valCurs.valutes) {
            list.add(adapter(item))
        }
        return list
    }

    private fun adapter(valute: Valute): Valuta {
        return Valuta(valute.id, valute.name, valute.charCode, valute.value, valute.nominal, valute.numCode)
    }
}