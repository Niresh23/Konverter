package com.nik.konverter.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nik.konverter.R
import com.nik.konverter.ui.valutes.ValuteActivity
import com.nik.konverter.model.forms.ValCurs
import com.nik.konverter.presenter.Presenter
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {



    companion object {
        private val ID_EXTRA = MainActivity::class.java.name + "ID_EXTRA"
        private val SIDE_EXTRA = MainActivity::class.java.name + "SIDE_EXTRA"
        fun start(context: Context, id: String, side: String) = Intent(context, ValuteActivity::class.java).run {
            putExtra(ID_EXTRA, id)
            putExtra(SIDE_EXTRA, side)
            context.startActivity(this)
        }
    }
    lateinit var presenter: Presenter

    private val id = intent.getStringExtra(ID_EXTRA)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_left.setOnClickListener{
            val SIDE = "left"
            ValuteActivity.start(this, SIDE, )
        }

    }

    interface RestApi {
        @GET("scripts/XML_daily.asp")
        fun getValute(): Single<ValCurs>
    }
}
