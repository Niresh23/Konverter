package com.nik.konverter.ui.valutes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.nik.konverter.R
import com.nik.konverter.adapters.ValuteRVAdapter
import com.nik.konverter.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_valute.*

class ValuteActivity: AppCompatActivity() {

    companion object {
        private val ID_EXTRA = ValuteActivity::class.java.name + "extraID"
        private val SIDE_EXTRA = ValuteActivity::class.java.name + "extraSIDE"
        fun start(context: Context, id: String, side: String) = Intent(context, ValuteActivity::class.java).run {
            putExtra(ID_EXTRA, id)
            putExtra(SIDE_EXTRA, side)
            context.startActivity(this)
        }
    }

    lateinit var adapter: ValuteRVAdapter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_valute)
        val id = intent.getStringExtra(ID_EXTRA)
        val side = intent.getStringExtra(SIDE_EXTRA)
        adapter = ValuteRVAdapter{
            MainActivity.start(this, it.id, side)
        }
        rv_valutes.layoutManager = GridLayoutManager(this, 1)
        rv_valutes.adapter = adapter
    }
}