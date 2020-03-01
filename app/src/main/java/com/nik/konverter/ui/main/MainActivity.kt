package com.nik.konverter.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.nik.konverter.R
import com.nik.konverter.model.forms.DataResult
import com.nik.konverter.model.forms.Valute
import com.nik.konverter.ui.base.BaseActivity
import com.nik.konverter.ui.valutes.ValuteActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<DataResult?, MainViewState>() {

    companion object {
        private val MESSAGE_EXTRA = MainActivity::class.java.name + "MESSAGE_EXTRA"
        private val ID_EXTRA = MainActivity::class.java.name + "ID_EXTRA"
        fun start(context: Context, message: String, id: String) = Intent(context, MainActivity::class.java).run {
            putExtra(MESSAGE_EXTRA, message)
            putExtra(ID_EXTRA, id)
            context.startActivity(this)
        }
    }

    override val model: MainViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_main
    var dataResult: DataResult? = null

    private val leftKeyListener = View.OnKeyListener {view, keyCode, event ->
        if((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            model.leftValueChanged(left_edit_text.text.toString(), dataResult)
        }
        return@OnKeyListener false
    }
    private val rightKeyListener = View.OnKeyListener {view, keyCode, event ->
        if((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            model.rightValueChanged(right_edit_text.text.toString(), dataResult)
        }
        return@OnKeyListener false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate()")
        model.getData(this)
        val message = intent.getStringExtra(MESSAGE_EXTRA)
        val id = intent.getStringExtra(ID_EXTRA)
        if (message == "left") {
            model.leftValutaChanged(this, id, dataResult)
        } else if (message == "right") {
            model.rightValutaChanged(this, id, dataResult)
        }

        btn_left.setOnClickListener {
            ValuteActivity.start(this, "left")
        }

        btn_right.setOnClickListener {
            ValuteActivity.start(this, "right")
        }

        left_edit_text.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) left_edit_text.text.clear()
        }

        right_edit_text.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) right_edit_text.text.clear()
        }
    }



    override fun renderData(data: DataResult?) {
        data?.let {
            dataResult = it
            dataResult?.apply {
                left_edit_text.setText(resultLeft)
                right_edit_text.setText(resultRight)
                left_text.text = charCodeLeft
                right_text.text = charCodeRight
                left_edit_text.setOnKeyListener(leftKeyListener)
                right_edit_text.setOnKeyListener(rightKeyListener)
            }
        }
    }

}
