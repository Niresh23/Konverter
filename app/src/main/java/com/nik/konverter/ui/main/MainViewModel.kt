package com.nik.konverter.ui.main

import android.content.Context
import android.util.Log
import com.nik.konverter.database.Valuta
import com.nik.konverter.model.Repository
import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.model.forms.DataResult
import com.nik.konverter.ui.base.BaseViewModel
import java.lang.Exception

class MainViewModel: BaseViewModel <DataResult?, MainViewState>() {
    private val ID_USD = "R01235"
    private val ID_RUB = "R99999"


    init {
        viewStateLiveData.value = MainViewState()
    }

    private val repository: Repository = Repository()
    var listValutas: List<Valuta>? = null

    fun getData(context: Context) {
        repository.getValutasResult(context).observeForever{
            when(it) {
                is ActionResult.Success<*> -> listValutas = it.data as List<Valuta>
                is ActionResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
            }
                viewStateLiveData.value = MainViewState(dataResult = process(listValutas, ID_RUB, ID_USD))
        }
    }

    fun leftValueChanged(enteredString: String, dataResult: DataResult?) {
        dataResult?.apply {

            val enteredValue: Float

            try {
                enteredValue = enteredString.toFloat()
                resultRight = (enteredValue * valueLeft!!/valueRight!!).toString()
                resultLeft = enteredString
                viewStateLiveData.value = MainViewState(dataResult = this)
            } catch(e: Exception) {
                viewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
            }
        }
    }

    fun rightValueChanged(enteredString: String, dataResult: DataResult?) {
        dataResult?.apply {

            val enteredValue: Float

            try {
                enteredValue = enteredString.toFloat()
                resultRight = (enteredValue * valueRight!!/valueLeft!!).toString()
                resultLeft = enteredString
                viewStateLiveData.value = MainViewState(dataResult = this)
            } catch(e: Exception) {
                viewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
            }
        }
    }

    fun rightValutaChanged(id: String, dataResult: DataResult?) {
        listValutas?.forEach{
            if (it.id == id) {
                try {
                    dataResult?.idRight = it.id
                    dataResult?.valueRight = it.value.replace(",", ".").toFloat()
                    dataResult?.charCodeRight = it.charCode
                    dataResult?.resultRight = (dataResult!!.resultLeft!!.toFloat() * dataResult.valueRight!!/ dataResult.valueLeft!!).toString()
                    viewStateLiveData.value = MainViewState(dataResult = dataResult)
                } catch(e: Exception) {
                    viewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
                }
                return@forEach
            }
        }
    }

    fun leftValutaChanged(id: String, dataResult: DataResult?) {
        listValutas?.forEach{
            if (it.id == id) {
                try {
                    dataResult?.idLeft = it.id
                    dataResult?.valueLeft = it.value.replace(",", ".").toFloat()
                    dataResult?.charCodeLeft = it.charCode
                    dataResult?.resultLeft = (dataResult!!.resultRight!!.toFloat() * dataResult.valueLeft!!/ dataResult.valueRight!!).toString()
                    viewStateLiveData.value = MainViewState(dataResult = dataResult)
                } catch(e: Exception) {
                    viewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
                }
                return@forEach
            }
        }
    }


    private fun process(list: List<Valuta>?, idLeft: String?, idRight:String?) = DataResult().apply {
        list?.forEach {

            if(it.id == idLeft) {
                valueLeft = it.value.replace(",", ".").toFloat()
                charCodeLeft = it.charCode
                resultLeft = it.value
            }

            if(it.id == idRight) {
                valueRight = it.value.replace(",", ".").toFloat()
                charCodeRight = it.charCode
                resultRight = it.value
            }
        }
    }

}