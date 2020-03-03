package com.nik.konverter.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nik.konverter.R
import androidx.lifecycle.ViewModelProvider

import com.nik.konverter.ui.valutes.ValutasFragment


class MainActivity: AppCompatActivity(), MainFragment.OnChangeListener,
    ValutasFragment.OnValutaChanged {

    companion object {
        private val MESSAGE_EXTRA = MainActivity::class.java.name + "MESSAGE_EXTRA"
        private val valutasFragment = ValutasFragment()
        private val mainFragment = MainFragment()
        fun start(context: Context, message: String) = Intent(context, MainActivity::class.java).run {
            putExtra(MESSAGE_EXTRA, message)
            context.startActivity(this)
        }
    }

    lateinit var model: MainViewModel
    val layoutRes: Int = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        model = ViewModelProvider(this).get(MainViewModel::class.java)
        val message = intent.getStringExtra(MESSAGE_EXTRA)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        model.getData(this)
        openFragment(mainFragment)
    }

    override fun leftValutaChanged() {
        val bundle = Bundle()
        bundle.putString("SIDE", "LEFT")
        valutasFragment.arguments = bundle
        openFragment(valutasFragment)
    }

    override fun rightValutaChanged() {
        val bundle = Bundle()
        bundle.putString("SIDE", "RIGHT")
        valutasFragment.arguments = bundle
        openFragment(valutasFragment)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment)
            .addToBackStack(null).commit()
    }

    private fun renderError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun onValutaChanged() {
        openFragment(mainFragment)
    }

}
