package dev.m13d.k0t1in.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import dev.m13d.k0t1in.ui.base.BaseActivity
import dev.m13d.k0t1in.ui.main.MainActivity

private const val START_DELAY = 1000L

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {
    override val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }
    override val layoutRes: Int = dev.m13d.k0t1in.R.layout.activity_splash
    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ viewModel.requestUser() }, START_DELAY)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}