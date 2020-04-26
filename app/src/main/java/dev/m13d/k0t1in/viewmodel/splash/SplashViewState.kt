package dev.m13d.k0t1in.viewmodel.splash

import dev.m13d.k0t1in.viewmodel.base.BaseViewState

class SplashViewState(isAuth: Boolean? = null, error: Throwable? = null) :
    BaseViewState<Boolean?>(isAuth, error)