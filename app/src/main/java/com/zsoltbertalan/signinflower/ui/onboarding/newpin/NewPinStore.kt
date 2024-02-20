package com.zsoltbertalan.signinflower.ui.onboarding.newpin

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.zsoltbertalan.signinflower.domain.api.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal fun newPinStore(
	storeFactory: StoreFactory,
	@MainDispatcher mainContext: CoroutineDispatcher,
	@IoDispatcher ioContext: CoroutineDispatcher,
	signInFlowerRepository: SignInFlowerRepository
):
	Store<Intent, State, Nothing> =
	storeFactory.create<Intent, Nothing, Message, State, Nothing>(
		name = "NewPinStore",
		initialState = State(),
		executorFactory = coroutineExecutorFactory {
			onIntent<Intent.NewPinChanged> {
				launch(ioContext) {
					signInFlowerRepository.saveUserPin(it.newPin)
					val canContinue = it.newPin.isNotEmpty()
					withContext(mainContext) { dispatch(Message.NewPinChanged(it.newPin, canContinue)) }
				}
			}
		},
		reducer = { msg ->
			when (msg) {
				is Message.NewPinChanged -> copy(newPin = msg.newPin, canContinue = msg.canContinue)
			}
		}
	)

sealed class Intent {
	data class NewPinChanged(val newPin: String) : Intent()
}

data class State(
	val newPin: String = "",
	val canContinue: Boolean = false
)

sealed class Message {
	data class NewPinChanged(val newPin: String, val canContinue: Boolean) : Message()
}
