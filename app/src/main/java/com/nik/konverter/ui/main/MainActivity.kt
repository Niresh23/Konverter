package com.nik.konverter.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.nik.konverter.R
import com.nik.konverter.model.forms.ActionResult
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

        }
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


    private fun initView() {
        left_edit_text.removeTextChangedListener(textChangeListener)
        right_edit_text.removeTextChangedListener(textChangeListener)
        left_edit_text.addTextChangedListener(textChangeListener)
        right_edit_text.addTextChangedListener(textChangeListener)
    }


}
