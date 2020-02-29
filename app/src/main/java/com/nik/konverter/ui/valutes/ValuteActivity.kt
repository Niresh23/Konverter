package com.nik.konverter.ui.valutes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.GridLayoutManager
import com.nik.konverter.R
import com.nik.konverter.adapters.ValuteRVAdapter
import com.nik.konverter.database.Valuta
import com.nik.konverter.ui.base.BaseActivity
import com.nik.konverter.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_valute.*
import org.koin.android.viewmodel.ext.android.viewModel

class ValuteActivity: BaseActivity<List<Valuta>?, ValuteViewState>() {

    companion object {
        private val MESSAGE_EXTRA = ValuteActivity::class.java.name + "MESSAGE_EXTRA"
        fun start(context: Context, id: String) = Intent(context, ValuteActivity::class.java).run {
            putExtra(MESSAGE_EXTRA, id)
            context.startActivity(this)
        }
    }

    override val model: ValuteViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_valute
    lateinit var adapter: ValuteRVAdapter

    override fun onResume() {
        super.onResume()
        model.update(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val message = intent.getStringExtra(MESSAGE_EXTRA)
        adapter = ValuteRVAdapter{
            MainActivity.start(this, message, it.id)
        }
        rv_valutes.layoutManager = GridLayoutManager(this, 1)
        rv_valutes.adapter = adapter
    }

    override fun renderData(data: List<Valuta>?) {
        data?.let {
            adapter.valutas = it
        }
    }


}