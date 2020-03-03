package com.nik.konverter.ui.valutes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nik.konverter.R
import com.nik.konverter.adapters.ValuteRVAdapter
import androidx.lifecycle.Observer
import com.nik.konverter.database.Valuta
import com.nik.konverter.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_valutas.*
import androidx.lifecycle.ViewModelProvider

class ValutasFragment: Fragment() {

    lateinit var onValutaChanged: OnValutaChanged
    val layoutRes: Int = R.layout.fragment_valutas
    lateinit var adapter: ValuteRVAdapter
    lateinit var model: MainViewModel
    private var side:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.apply {
            model = ViewModelProvider(activity!!).get(MainViewModel::class.java)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onValutaChanged = context as OnValutaChanged
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        side = arguments?.getString("SIDE")
        return inflater.inflate(layoutRes, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getValutasViewState().observe(viewLifecycleOwner, Observer {viewState ->
            viewState.error?.let {
                renderError(it)
                return@Observer
            }
            renderData(viewState.valutas)
        })
        model.updateValutasViewState(this.context!!)
        adapter = ValuteRVAdapter{
            if(side == "LEFT") model.leftValutaChanged(this.activity!!, it.id)
            else if(side == "RIGHT") model.rightValutaChanged(this.activity!!, it.id)
            onValutaChanged.onValutaChanged()
        }
        rv_valutes.layoutManager = GridLayoutManager(this.context, 1)
        rv_valutes.adapter = adapter
    }

    private fun renderData(data: List<Valuta>?) {
        data?.let {
            adapter.valutas = it
        }

    }

    private fun renderError(error: Throwable) {
        Toast.makeText(this.context, error.message, Toast.LENGTH_SHORT).show()
    }
    interface OnValutaChanged {
        fun onValutaChanged()
    }
}