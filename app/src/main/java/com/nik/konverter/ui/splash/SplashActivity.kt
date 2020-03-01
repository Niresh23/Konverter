package com.nik.konverter.ui.splash

import android.util.Log
import com.nik.konverter.ui.base.BaseActivity
import com.nik.konverter.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity: BaseActivity<String?, SplashViewState>(){

    override val model: SplashViewModel by viewModel()
    override val layoutRes: Int? = null



    override fun onResume() {
        super.onResume()
        Log.d("SplashActivity", "renderData()")
        model.update(this)
    }
    override fun renderData(data: String?) {
        data?.let {
            Log.d("SplashActivity", "renderData($it)")
            MainActivity.start(this, data, "1")
        }
    }


}