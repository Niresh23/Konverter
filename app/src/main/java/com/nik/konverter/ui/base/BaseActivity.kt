package com.nik.konverter.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

abstract class BaseActivity<T, S: BaseViewState<T>>: AppCompatActivity() {

    abstract val model: BaseViewModel<T, S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let {
            setContentView(it)
        }
        Log.d("BaseActivity", "onCreate()")
        model.getViewState().observe(this, Observer<S> {viewState ->
            Log.d("BaseActivity", "getViewState().observe()")
            viewState.error?.let {
                renderError(it)
                return@Observer
            }
            renderData(viewState.data)
        }
        )

    }

    abstract fun renderData(data: T)
    private fun renderError(error: Throwable) {
        Log.d("BaseActivity", error.message)
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()}
}