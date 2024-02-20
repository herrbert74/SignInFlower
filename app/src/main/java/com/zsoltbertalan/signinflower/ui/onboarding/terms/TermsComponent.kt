package com.zsoltbertalan.signinflower.ui.onboarding.terms

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.zsoltbertalan.signinflower.di.MainDispatcher
import com.zsoltbertalan.signinflower.ext.asValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface TermsComp {

	fun onBackClicked()

	fun acceptTerms()

	fun onForwardClicked()

	sealed class Output {
		data object Back : Output()
		data object Forward : Output()
	}

	val state: Value<State>

}

class TermsComponent(
	componentContext: ComponentContext,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	private val output: FlowCollector<TermsComp.Output>,
) : TermsComp, ComponentContext by componentContext {

	private var termsStore = termsStore(LoggingStoreFactory(DefaultStoreFactory()))

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(TermsComp.Output.Back)
		}
	}

	override fun acceptTerms() {
		termsStore.accept(Intent.AcceptTerms)
	}

	override fun onForwardClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(TermsComp.Output.Forward)
		}
	}

	override val state: Value<State>
		get() = termsStore.asValue()

}
