package com.zsoltbertalan.signinflower.ui.onboarding.credentials

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zsoltbertalan.signinflower.data.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import com.zsoltbertalan.signinflower.ext.asValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface CredentialsComp {

	fun onBackClicked()

	fun emailChanged(email:String)

	fun passwordChanged(password:String)

	fun onForwardClicked()

	sealed class Output {
		data object Back : Output()
		data object Forward : Output()
	}

	val state: Value<State>

}

class CredentialsComponent(
	componentContext: ComponentContext,
	signInFlowerRepository: SignInFlowerRepository,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val output: FlowCollector<CredentialsComp.Output>,
) : CredentialsComp, ComponentContext by componentContext {

	private var credentialsStore = credentialsStore(
		LoggingStoreFactory(DefaultStoreFactory()),
		mainDispatcher,
		ioDispatcher,
		signInFlowerRepository,
		stateKeeper
	)

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(CredentialsComp.Output.Back)
		}
	}

	override fun emailChanged(email: String) {
		credentialsStore.accept(Intent.EmailChanged(email))
	}

	override fun passwordChanged(password: String) {
		credentialsStore.accept(Intent.PasswordChanged(password))
	}

	override fun onForwardClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(CredentialsComp.Output.Forward)
		}
	}

	override val state: Value<State>
		get() = credentialsStore.asValue()

}
