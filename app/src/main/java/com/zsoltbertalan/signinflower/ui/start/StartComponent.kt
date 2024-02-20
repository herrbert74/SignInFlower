package com.zsoltbertalan.signinflower.ui.start

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

interface StartComp {

	fun onPushWelcome()

	fun onPushMain()

	val state: Value<State>

	sealed class Output {
		data object PushWelcome : Output()
		data object PushMain : Output()
	}

}

class StartComponent(
	componentContext: ComponentContext,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
	signInFlowerRepository: SignInFlowerRepository,
	private val output: FlowCollector<StartComp.Output>,
) : StartComp, ComponentContext by componentContext {

	private var startStore = startStore(
		LoggingStoreFactory(DefaultStoreFactory()),
		mainDispatcher,
		ioDispatcher,
		signInFlowerRepository
	)

	override fun onPushWelcome() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(StartComp.Output.PushWelcome)
		}
	}

	override fun onPushMain() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(StartComp.Output.PushMain)
		}
	}

	override val state: Value<State>
		get() = startStore.asValue()

}
