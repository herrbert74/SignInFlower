package com.zsoltbertalan.signinflower.ui.onboarding.welcome

import com.arkivanov.decompose.ComponentContext
import com.zsoltbertalan.signinflower.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface WelcomeComp {

	fun onBackClicked()

	fun onForwardClicked()

	sealed class Output {
		data object Forward : Output()
	}

}

class WelcomeComponent(
	componentContext: ComponentContext,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	private val output: FlowCollector<WelcomeComp.Output>,
	private val finishHandler: () -> Unit,
) : WelcomeComp, ComponentContext by componentContext {

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			finishHandler.invoke()
		}
	}

	override fun onForwardClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(WelcomeComp.Output.Forward)
		}
	}

}
