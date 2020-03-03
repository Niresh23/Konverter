package com.nik.konverter.ui.splash

import android.os.Bundle
import android.util.Log
import com.nik.konverter.ui.base.BaseActivity
import com.nik.konverter.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity: BaseActivity<String?, SplashViewState>(){

    override val model: SplashViewModel by viewModel()
    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        model.update(this)
    }
    override fun renderData(data: String?) {
        data?.let {
            MainActivity.start(this, it)
        }
    }


}