package com.nik.konverter.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.nik.konverter.R
import com.nik.konverter.model.forms.DataResult
import com.nik.konverter.ui.base.BaseActivity
import com.nik.konverter.ui.valutes.ValuteActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<DataResult?, MainViewState>() {

    companion object {
        private val MESSAGE_EXTRA = MainActivity::class.java.name + "MESSAGE_EXTRA"
        fun start(context: Context, message: String) = Intent(context, MainActivity::class.java).run {
            putExtra(MESSAGE_EXTRA, message)
            context.startActivity(this)
        }
    }

    override val model: MainViewModel by viewModel()
    override val layoutRes: Int = R.layout.activity_main
    var dataResult: DataResult? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val message = intent.getStringExtra(MESSAGE_EXTRA)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        btn_left.setOnClickListener{
            ValuteActivity.start(this, "left")
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
            }
        }
    }

    private val textChangeListenerLeft = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
    }

    private val textChangeListenerRight = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            model.rightValueChanged(left_edit_text.text.toString(), dataResult)
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }


    private fun initView() {
        left_edit_text.removeTextChangedListener(textChangeListenerLeft)
        right_edit_text.removeTextChangedListener(textChangeListenerRight)
        left_edit_text.addTextChangedListener(textChangeListenerLeft)
        right_edit_text.addTextChangedListener(textChangeListenerRight)
    }


}
