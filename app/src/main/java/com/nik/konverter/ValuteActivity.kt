package com.nik.konverter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.nik.konverter.adapters.ValuteRVAdapter

class ValuteActivity: AppCompatActivity() {

    companion object {
        private val POSITION = "position"
        fun start(context: Context, position: Int) = Intent(context, ValuteActivity::class.java).run {
            putExtra(POSITION, position)
            context.startActivity(this)
        }
    }

    lateinit var adapter: ValuteRVAdapter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_valute)
    }
}