package dev.m13d.k0t1in

import androidx.multidex.MultiDexApplication
import dev.m13d.k0t1in.di.appModule
import dev.m13d.k0t1in.di.mainModule
import dev.m13d.k0t1in.di.noteModule
import dev.m13d.k0t1in.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, splashModule, mainModule, noteModule)
        }
//        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}