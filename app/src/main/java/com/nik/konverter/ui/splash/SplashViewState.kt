package com.nik.konverter.ui.splash

import com.nik.konverter.model.forms.ActionResult
import com.nik.konverter.ui.base.BaseViewState

class SplashViewState(val string: String? = null, error: Throwable? = null): BaseViewState<String?>(string, error)