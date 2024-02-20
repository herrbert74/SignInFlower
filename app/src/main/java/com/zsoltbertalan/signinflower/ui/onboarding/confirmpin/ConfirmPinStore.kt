package com.zsoltbertalan.signinflower.ui.onboarding.confirmpin

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.zsoltbertalan.signinflower.data.network.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal fun confirmPinStore(
	storeFactory: StoreFactory,
	@MainDispatcher mainContext: CoroutineDispatcher,
	@IoDispatcher ioContext: CoroutineDispatcher,
	signInFlowerRepository: SignInFlowerRepository
): Store<Intent, State, Nothing> = storeFactory.create<Intent, Nothing, Message, State, Nothing>(
	name = "ConfirmPinStore",
	initialState = State(),
	executorFactory = coroutineExecutorFactory {
		onIntent<Intent.PinChanged> {
			launch(ioContext) {
				val firstPin = signInFlowerRepository.getUserPin()
				val canContinue = it.confirmPin.isNotEmpty() && firstPin == it.confirmPin
				withContext(mainContext) { dispatch(Message.ConfirmPinChanged(it.confirmPin, canContinue)) }
			}
		}
	},
	reducer = { msg ->
		when (msg) {
			is Message.ConfirmPinChanged -> copy(confirmPin = msg.confirmPin, canContinue = msg.canContinue)
		}
	}
)

sealed class Intent {
	data class PinChanged(val confirmPin: String) : Intent()
}

data class State(
	val confirmPin: String = "",
	val canContinue: Boolean = false
)

sealed class Message {
	data class ConfirmPinChanged(val confirmPin: String, val canContinue: Boolean) : Message()
}
