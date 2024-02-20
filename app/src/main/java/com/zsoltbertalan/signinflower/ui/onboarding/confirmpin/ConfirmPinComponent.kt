package com.zsoltbertalan.signinflower.ui.onboarding.confirmpin

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zsoltbertalan.signinflower.data.network.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import com.zsoltbertalan.signinflower.ext.asValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface ConfirmPinComp {

	fun onBackClicked()

	fun emailChanged(email: String)

	fun onForwardClicked()

	sealed class Output {
		data object Back : Output()
		data object Forward : Output()
	}

	val state: Value<State>

}

class ConfirmPinComponent(
	componentContext: ComponentContext,
	signInFlowerRepository: SignInFlowerRepository,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	@IoDispatcher ioDispatcher: CoroutineDispatcher,
	private val output: FlowCollector<ConfirmPinComp.Output>,
) : ConfirmPinComp, ComponentContext by componentContext {

	private var confirmPinStore = confirmPinStore(
		LoggingStoreFactory(DefaultStoreFactory()),
		mainDispatcher,
		ioDispatcher,
		signInFlowerRepository
	)

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(ConfirmPinComp.Output.Back)
		}
	}

	override fun emailChanged(email: String) {
		confirmPinStore.accept(Intent.PinChanged(email))
	}

	override fun onForwardClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(ConfirmPinComp.Output.Forward)
		}
	}

	override val state: Value<State>
		get() = confirmPinStore.asValue()

}
