package com.zsoltbertalan.signinflower.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.DefaultComponentContext
import com.zsoltbertalan.signinflower.domain.api.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import com.zsoltbertalan.signinflower.root.SignInFlowerRootComponent
import com.zsoltbertalan.signinflower.root.SignInFlowerRootContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@AndroidEntryPoint
class SignInFlowerActivity : ComponentActivity() {

    @Inject
    lateinit var signInFlowerRepository: SignInFlowerRepository

    @Inject
    @MainDispatcher
    lateinit var mainContext: CoroutineDispatcher

    @Inject
    @IoDispatcher
    lateinit var ioContext: CoroutineDispatcher

    private lateinit var signInFlowerRootComponent: SignInFlowerRootComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInFlowerRootComponent = SignInFlowerRootComponent(
            DefaultComponentContext(lifecycle, savedStateRegistry, viewModelStore, null),
            mainContext,
            ioContext,
            signInFlowerRepository
        ) { finish() }

        setContent {
            SignInFlowerRootContent(signInFlowerRootComponent)
        }
    }

}
