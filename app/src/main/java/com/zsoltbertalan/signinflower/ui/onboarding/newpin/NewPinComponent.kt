package com.zsoltbertalan.signinflower.ui.onboarding.newpin

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zsoltbertalan.signinflower.domain.api.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import com.zsoltbertalan.signinflower.ext.asValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface NewPinComp {

	fun onBackClicked()

	fun newPinChanged(newPin: String)

	fun onForwardClicked()

	sealed class Output {
		data object Back : Output()
		data object Forward : Output()
	}

	val state: Value<State>

}

class NewPinComponent(
	componentContext: ComponentContext,
	signInFlowerRepository: SignInFlowerRepository,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	@IoDispatcher ioContext: CoroutineDispatcher,
	private val output: FlowCollector<NewPinComp.Output>,
) : NewPinComp, ComponentContext by componentContext {

	private var newPinStore = newPinStore(
		LoggingStoreFactory(DefaultStoreFactory()),
		mainDispatcher,
		ioContext,
		signInFlowerRepository
	)

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(NewPinComp.Output.Back)
		}
	}

	override fun newPinChanged(newPin: String) {
		newPinStore.accept(Intent.NewPinChanged(newPin))
	}

	override fun onForwardClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(NewPinComp.Output.Forward)
		}
	}

	override val state: Value<State>
		get() = newPinStore.asValue()

}
