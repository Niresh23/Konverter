package com.nik.konverter.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*
import androidx.lifecycle.Observer
import com.nik.konverter.R
import com.nik.konverter.model.forms.DataResult
import androidx.lifecycle.ViewModelProvider

class MainFragment: Fragment() {

    lateinit var model: MainViewModel
    private lateinit var listener: OnChangeListener
    private val leftKeyListener = View.OnKeyListener { _, keyCode, event ->
        if((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            model.leftValueChanged(left_edit_text.text.toString())
        }
        return@OnKeyListener false
    }
    private val rightKeyListener = View.OnKeyListener { _, keyCode, event ->
        if((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            model.rightValueChanged(right_edit_text.text.toString())
        }
        return@OnKeyListener false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(activity!!).get(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as OnChangeListener
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getMainViewState().observe(viewLifecycleOwner, Observer<MainViewState> {viewState ->
            viewState.error?.let {
                renderError(it)
                return@Observer
            }
            renderData(viewState.data)
        })
        model.getData(this.context!!)
        btn_left.setOnClickListener {
            listener.leftValutaChanged()
        }

        btn_right.setOnClickListener {
            listener.rightValutaChanged()
        }

        left_edit_text.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) left_edit_text.text?.clear()
        }

        right_edit_text.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) right_edit_text.text?.clear()
        }
    }

    private fun renderData(data: DataResult?) {
        data?.let {
            it.apply {
                left_edit_text.setText(resultLeft)
                right_edit_text.setText(resultRight)
                left_text.text = charCodeLeft
                right_text.text = charCodeRight
                left_edit_text.setOnKeyListener(leftKeyListener)
                right_edit_text.setOnKeyListener(rightKeyListener)
            }
        }
    }

    private fun renderError(error: Throwable) {
        Toast.makeText(this.context, error.message, Toast.LENGTH_SHORT).show()
    }

    interface OnChangeListener {
        fun leftValutaChanged()
        fun rightValutaChanged()
    }

}