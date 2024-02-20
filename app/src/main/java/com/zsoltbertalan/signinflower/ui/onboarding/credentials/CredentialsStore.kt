package com.zsoltbertalan.signinflower.ui.onboarding.credentials

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.consume
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.zsoltbertalan.signinflower.data.network.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize

internal fun credentialsStore(
	storeFactory: StoreFactory,
	@MainDispatcher mainContext: CoroutineDispatcher,
	@IoDispatcher ioContext: CoroutineDispatcher,
	signInFlowerRepository: SignInFlowerRepository,
	stateKeeper: StateKeeper,
): Store<Intent, State, Nothing> =
	storeFactory.create<Intent, Nothing, Message, State, Nothing>(
		name = "CredentialsStore",
		initialState = stateKeeper.consume(key = "CredentialsStore") ?: State(),
		executorFactory = coroutineExecutorFactory {
			onIntent<Intent.EmailChanged> {
				launch(ioContext) {
					signInFlowerRepository.saveUserEmail(it.email)
					val canContinue = it.email.isNotEmpty() && state.password.isNotEmpty()
					withContext(mainContext) { dispatch(Message.EmailChanged(it.email, canContinue)) }
				}
			}
			onIntent<Intent.PasswordChanged> {
				launch(ioContext) {
					signInFlowerRepository.saveUserPassword(it.password)
					val canContinue = it.password.isNotEmpty() && state.email.isNotEmpty()
					withContext(mainContext) { dispatch(Message.PasswordChanged(it.password, canContinue)) }
				}
			}
		},
		reducer = { msg ->
			when (msg) {
				is Message.EmailChanged -> copy(email = msg.email, canContinue = msg.canContinue)
				is Message.PasswordChanged -> copy(password = msg.password, canContinue = msg.canContinue)
			}
		}
	).also {
		stateKeeper.register("CredentialsStore") {
			it.state.copy()
		}
	}

sealed class Intent {
	data class EmailChanged(val email: String) : Intent()
	data class PasswordChanged(val password: String) : Intent()
}

@Parcelize
data class State(
	val email: String = "",
	val password: String = "",
	val canContinue: Boolean = false
) : Parcelable

sealed class Message {
	data class EmailChanged(val email: String, val canContinue: Boolean) : Message()
	data class PasswordChanged(val password: String, val canContinue: Boolean) : Message()
}
