package com.zsoltbertalan.signinflower.ui.start

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

internal fun startStore(
	storeFactory: StoreFactory,
	@MainDispatcher mainContext: CoroutineDispatcher,
	@IoDispatcher ioContext: CoroutineDispatcher,
	signInFlowerRepository: SignInFlowerRepository
): Store<Nothing, State, Nothing> = storeFactory.create<Nothing, BootstrapIntent, Message, State, Nothing>(
	name = "StartStore",
	initialState = State(),
	bootstrapper = coroutineBootstrapper {
		launch(mainContext) {
			dispatch(BootstrapIntent.BootStrap)
		}
	},
	executorFactory = coroutineExecutorFactory {
		onAction<BootstrapIntent.BootStrap> {
			launch(ioContext) {
				val isLoggedIn = signInFlowerRepository.isUserSignedIn()
				withContext(mainContext) {
					dispatch(Message.IsLoggedIn(isLoggedIn))
				}
			}
		}
	},
	reducer = { msg ->
		when (msg) {
			is Message.IsLoggedIn -> copy(isLoggedIn = msg.isLoggedIn)
		}
	}
)

data class State(
	val isLoggedIn: Boolean? = null
)

sealed class Message {
	data class IsLoggedIn(val isLoggedIn: Boolean) : Message()
}

sealed class BootstrapIntent {
	data object BootStrap : BootstrapIntent()
}
