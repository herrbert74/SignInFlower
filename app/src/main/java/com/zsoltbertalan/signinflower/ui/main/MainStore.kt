package com.zsoltbertalan.signinflower.ui.main

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.zsoltbertalan.signinflower.data.network.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal fun mainStore(
	storeFactory: StoreFactory,
	@MainDispatcher mainContext: CoroutineDispatcher,
	@IoDispatcher ioContext: CoroutineDispatcher,
	signInFlowerRepository: SignInFlowerRepository
): Store<Intent, State, Nothing> = storeFactory.create<Intent, BootstrapIntent, Message, State, Nothing>(
	name = "MainStore",
	initialState = State(),
	bootstrapper = coroutineBootstrapper {
		launch(mainContext) {
			dispatch(BootstrapIntent.BootStrap)
		}
	},
	executorFactory = coroutineExecutorFactory {
		onAction<BootstrapIntent.BootStrap> {
			launch(ioContext) {
				val firstName = signInFlowerRepository.getUserFirstName()
				val lastName = signInFlowerRepository.getUserLastName()
				withContext(mainContext) {
					dispatch(Message.InitialLoad(firstName, lastName))
				}
			}
		}
		onIntent<Intent.SignOut> {
			launch(ioContext) {
				signInFlowerRepository.deleteAllUserData()
				withContext(mainContext) {
					dispatch(Message.SignOut)
				}
			}
		}
	},
	reducer = { msg ->
		when (msg) {
			is Message.InitialLoad -> copy(firstName = msg.firstName, lastName = msg.lastName, isLoading = false)
			is Message.SignOut -> copy(isSignedOut = true)
		}
	}
)

sealed class Intent {
	data object SignOut : Intent()
}

data class State(
	val firstName: String? = null,
	val lastName: String? = null,
	val isLoading: Boolean = true,
	val isSignedOut: Boolean = false,
)

sealed class Message {
	data class InitialLoad(val firstName: String, val lastName: String) : Message()
	data object SignOut : Message()
}

sealed class BootstrapIntent {
	data object BootStrap : BootstrapIntent()
}
