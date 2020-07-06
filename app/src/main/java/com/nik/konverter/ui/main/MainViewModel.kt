package com.nik.konverter.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nik.konverter.database.Valuta
import com.nik.konverter.model.Repository
import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.model.forms.DataResult
import com.nik.konverter.ui.valutes.ValutasViewState
import java.lang.Exception

class MainViewModel constructor(private val repository: Repository): ViewModel() {

    private val ID_USD = "R01235"
    private val ID_RUB = "R99999"
    private val mainViewStateLiveData = MutableLiveData<MainViewState>()
    private val valutasViewStateLiveData = MutableLiveData<ValutasViewState>()
    fun getMainViewState(): LiveData<MainViewState> = mainViewStateLiveData
    fun getValutasViewState(): LiveData<ValutasViewState> = valutasViewStateLiveData
    private var dataResult: DataResult? = null
    private var valutasList: List<Valuta>? = null
    init {
        mainViewStateLiveData.value = MainViewState()
        valutasViewStateLiveData.value = ValutasViewState()
    }

    fun updateValutasViewState() {
        repository.getValutasResult().observeForever { t: ActionResult? ->
            t ?: return@observeForever
            when(t) {
                is ActionResult.Success<*> -> valutasViewStateLiveData.value = ValutasViewState( valutas = t.data as List<Valuta>)
                is ActionResult.Error -> valutasViewStateLiveData.value = ValutasViewState(error = t.error)
            }
        }
    }

    fun getData(context: Context) {
        repository.getValutasResult().observeForever{
            when(it) {
                is ActionResult.Success<*> -> {
                    if (dataResult == null) dataResult = process(it.data as List<Valuta>, ID_RUB, ID_USD)
                    if(valutasList == null) valutasList = it.data as List<Valuta>
                    mainViewStateLiveData.value = MainViewState(dataResult = dataResult)
                }
                is ActionResult.Error -> mainViewStateLiveData.value = MainViewState(error = it.error)
            }
        }
    }

    fun leftValueChanged(enteredString: String) {
        dataResult?.apply {
            val enteredValue: Float
            try {
                enteredValue = enteredString.toFloat()
                resultRight = (enteredValue * valueLeft/valueRight).toString()
                resultLeft = enteredString
                mainViewStateLiveData.value = MainViewState(dataResult = this)
            } catch(e: Exception) {
                mainViewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
            }
        }
    }

    fun rightValueChanged(enteredString: String) {
        dataResult?.apply {
            val enteredValue: Float
            try {
                enteredValue = enteredString.toFloat()
                resultRight = enteredString
                resultLeft = (enteredValue * valueRight/valueLeft).toString()
                mainViewStateLiveData.value = MainViewState(dataResult = this)
            } catch(e: Exception) {
                mainViewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
            }
        }
    }

    fun rightValutaChanged(context: Context, idEntered: String) {
        try {
            valutasList?.forEach {
                if (it.id == idEntered) {
                    dataResult?.idRight = it.id
                    dataResult?.valueRight = it.value.replace(",", ".").toFloat()
                    dataResult?.charCodeRight = it.charCode
                    dataResult?.resultRight =
                                    (dataResult!!.resultLeft!!.toFloat() * dataResult!!.valueLeft / dataResult!!.valueRight).toString()
                    mainViewStateLiveData.value = MainViewState(dataResult = dataResult)
                                return@forEach
                }
            }
        } catch (e: Exception) {
            mainViewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
        }
    }

    fun leftValutaChanged(context: Context, idEntered: String) {
        try {
            valutasList?.forEach {
                if (it.id == idEntered) {
                    dataResult?.idLeft = it.id
                    dataResult?.valueLeft = it.value.replace(",", ".").toFloat()
                    dataResult?.charCodeLeft = it.charCode
                    dataResult?.resultLeft = (dataResult!!.resultRight!!.toFloat() * dataResult!!.valueRight!!/dataResult!!.valueLeft).toString()
                    mainViewStateLiveData.value = MainViewState(dataResult = dataResult)
                    return@forEach
                }
            }
        } catch (e: Exception) {
            mainViewStateLiveData.value = MainViewState(error = Exception("Некорректно введено значение"))
        }
    }


    private fun process(list: List<Valuta>?, idL: String?, idR:String?) = DataResult().apply {
        list?.forEach {

            if(it.id == idL) {
                idLeft = idL
                valueLeft = it.value.replace(",", ".").toFloat()
                charCodeLeft = it.charCode
                resultRight = it.value
            }

            if(it.id == idR) {
                idRight = idR
                valueRight = it.value.replace(",", ".").toFloat()
                charCodeRight = it.charCode
                resultLeft = it.value
            }
        }
    }
}