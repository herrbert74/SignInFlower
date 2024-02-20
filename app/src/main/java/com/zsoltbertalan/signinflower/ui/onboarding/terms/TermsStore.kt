package com.zsoltbertalan.signinflower.ui.onboarding.terms

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory

internal fun termsStore(storeFactory: StoreFactory): Store<Intent, State, Nothing> =
	storeFactory.create<Intent, Nothing, Message, State, Nothing>(
		name = "TermsStore",
		initialState = State(),
		executorFactory = coroutineExecutorFactory {
			onIntent<Intent.AcceptTerms> { dispatch(Message.TermsConfirmed) }
		},
		reducer = { msg ->
			when (msg) {
				is Message.TermsConfirmed -> copy(areTermsConfirmed = true)
			}
		}
	)

sealed class Intent {
	data object AcceptTerms : Intent()
}

data class State(
	val areTermsConfirmed: Boolean = false
)

sealed class Message {
	data object TermsConfirmed : Message()
}
