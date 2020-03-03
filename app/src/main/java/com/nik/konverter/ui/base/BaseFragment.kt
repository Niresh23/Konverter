package com.nik.konverter.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment

abstract class BaseFragment<T, S:BaseViewState<T>>: Fragment() {

    abstract val model: BaseViewModel<T, S>
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.getViewState().observe(activity!!, Observer<S> { viewState ->
            viewState.error?.let {
                renderError(it)
                return@Observer
            }
            renderData(viewState.data)
        }
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    abstract fun renderData(data: T)
    private fun renderError(error: Throwable) {
        Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
    }
}