package com.zsoltbertalan.signinflower.ui.main

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

interface MainComp {

	fun onBackClicked()

	fun onSignOutClicked()

	fun onSignOut()

	sealed class Output {
		data object SignOut : Output()
	}

	val state: Value<State>

}

class MainComponent(
	componentContext: ComponentContext,
	signInFlowerRepository: SignInFlowerRepository,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
	private val output: FlowCollector<MainComp.Output>,
	private val finishHandler: () -> Unit,
) : MainComp, ComponentContext by componentContext {

	private var mainStore = mainStore(
		LoggingStoreFactory(DefaultStoreFactory()),
		mainDispatcher,
		ioDispatcher,
		signInFlowerRepository
	)

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			finishHandler.invoke()
			mainStore.dispose()
		}
	}

	override fun onSignOutClicked() {
		mainStore.accept(Intent.SignOut)
	}

	override fun onSignOut() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(MainComp.Output.SignOut)
		}
	}

	override val state: Value<State>
		get() = mainStore.asValue()

}
