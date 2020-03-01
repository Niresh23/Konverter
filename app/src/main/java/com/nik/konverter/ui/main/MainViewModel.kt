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

    fun getData(context: Context) {
        Log.d("MainViewModel", "getData()")
        repository.getValutasResult(context).observeForever{
            when(it) {

                is ActionResult.Success<*> -> {
                    Log.d("MainViewModel", "getData().observeForever().Success")
                    viewStateLiveData.value = MainViewState(dataResult = process(it.data as List<Valuta>?, ID_RUB, ID_USD))
                }
                is ActionResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
            }

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
                resultRight = enteredString
                resultLeft = (enteredValue * valueRight!!/valueLeft!!).toString()
                viewStateLiveData.value = MainViewState(dataResult = this)
            } catch(e: Exception) {
                viewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
            }
        }
    }

    fun rightValutaChanged(context: Context, id: String, dataResult: DataResult?) {
        repository.getValutasResult(context).observeForever{
            when(it) {
                is ActionResult.Success<*> -> {
                    try {
                        (it.data as List<Valuta>?)?.forEach {
                            if (it.id == id) {

                                dataResult?.idRight = it.id
                                dataResult?.valueRight = it.value.replace(",", ".").toFloat()
                                dataResult?.charCodeRight = it.charCode
                                dataResult?.resultRight =
                                    (dataResult!!.resultLeft!!.toFloat() * dataResult.valueRight!! / dataResult.valueLeft!!).toString()
                                viewStateLiveData.value = MainViewState(dataResult = dataResult)
                            }
                            return@forEach
                        }
                    } catch (e: Exception) {
                        viewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
                    }
                }
                is ActionResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
            }

        }

    }

    fun leftValutaChanged(context: Context, id: String, dataResult: DataResult?) {
        repository.getValutasResult(context).observeForever{
            when(it) {
                is ActionResult.Success<*> -> {
                    try {
                        (it.data as List<Valuta>?)?.forEach {
                            if (it.id == id) {
                                dataResult?.idLeft = it.id
                                dataResult?.valueLeft = it.value.replace(",", ".").toFloat()
                                dataResult?.charCodeLeft = it.charCode
                                dataResult?.resultLeft = (dataResult!!.resultRight!!.toFloat() * dataResult.valueLeft!!/ dataResult.valueRight!!).toString()
                                viewStateLiveData.value = MainViewState(dataResult = dataResult)
                            }
                            return@forEach
                        }
                    } catch (e: Exception) {
                        viewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
                    }
                }
                is ActionResult.Error -> viewStateLiveData.value = MainViewState(error = it.error)
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

    override fun onCleared() {
        super.onCleared()

    }


}