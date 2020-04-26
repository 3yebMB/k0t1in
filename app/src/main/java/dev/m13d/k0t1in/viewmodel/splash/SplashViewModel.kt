package dev.m13d.k0t1in.ui.splash

import dev.m13d.k0t1in.data.errors.NoAuthException
import dev.m13d.k0t1in.model.Repository
import dev.m13d.k0t1in.ui.base.BaseViewModel

class SplashViewModel(private val repository: Repository = Repository) :
        BaseViewModel<Boolean?, SplashViewState>() {
    fun requestUser() {
        repository.getCurrentUser().observeForever {
            viewStateLiveData.value = if (it != null) {
                SplashViewState(isAuth = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}