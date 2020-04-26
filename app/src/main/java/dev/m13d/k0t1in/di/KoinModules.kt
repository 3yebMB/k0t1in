package dev.m13d.k0t1in.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.m13d.k0t1in.data.provider.FireStoreProvider
import dev.m13d.k0t1in.data.provider.RemoteDataProvider
import dev.m13d.k0t1in.model.Repository
import dev.m13d.k0t1in.viewmodel.splash.SplashViewModel
import dev.m13d.k0t1in.viewmodel.main.MainViewModel
import dev.m13d.k0t1in.viewmodel.note.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { Repository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}