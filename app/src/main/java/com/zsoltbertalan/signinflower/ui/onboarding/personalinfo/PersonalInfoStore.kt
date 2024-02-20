package com.zsoltbertalan.signinflower.ui.onboarding.personalinfo

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.zsoltbertalan.signinflower.domain.api.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal fun personalInfoStore(
	storeFactory: StoreFactory,
	@MainDispatcher mainContext: CoroutineDispatcher,
	@IoDispatcher ioContext: CoroutineDispatcher,
	signInFlowerRepository: SignInFlowerRepository
): Store<Intent, State, Nothing> =
	storeFactory.create<Intent, Nothing, Message, State, Nothing>(
		name = "PersonalInfoStore",
		initialState = State(),
		executorFactory = coroutineExecutorFactory {
			onIntent<Intent.FirstNameChanged> {
				launch(ioContext) {
					signInFlowerRepository.saveUserFirstName(it.firstName)
					val canContinue =
						it.firstName.isNotEmpty() && state.lastName.isNotEmpty() && state.phoneNumber.isNotEmpty()
					withContext(mainContext) { dispatch(Message.FirstNameChanged(it.firstName, canContinue)) }
				}
			}
			onIntent<Intent.LastNameChanged> {
				launch(ioContext) {
					signInFlowerRepository.saveUserLastName(it.lastName)
					val canContinue =
						it.lastName.isNotEmpty() && state.firstName.isNotEmpty() && state.phoneNumber.isNotEmpty()
					withContext(mainContext) { dispatch(Message.LastNameChanged(it.lastName, canContinue)) }
				}
			}
			onIntent<Intent.PhoneNumberChanged> {
				signInFlowerRepository.saveUserPhoneNumber(it.phoneNumber)
				val canContinue =
					it.phoneNumber.isNotEmpty() && state.firstName.isNotEmpty() && state.lastName.isNotEmpty()
				dispatch(Message.PhoneNumberChanged(it.phoneNumber, canContinue))
			}
		},
		reducer = { msg ->
			when (msg) {
				is Message.FirstNameChanged -> copy(firstName = msg.email, canContinue = msg.canContinue)
				is Message.LastNameChanged -> copy(lastName = msg.password, canContinue = msg.canContinue)
				is Message.PhoneNumberChanged -> copy(phoneNumber = msg.phoneNumber, canContinue = msg.canContinue)
			}
		}
	)

sealed class Intent {
	data class FirstNameChanged(val firstName: String) : Intent()
	data class LastNameChanged(val lastName: String) : Intent()
	data class PhoneNumberChanged(val phoneNumber: String) : Intent()
}

data class State(
	val firstName: String = "",
	val lastName: String = "",
	val phoneNumber: String = "",
	val canContinue: Boolean = false
)

sealed class Message {
	data class FirstNameChanged(val email: String, val canContinue: Boolean) : Message()
	data class LastNameChanged(val password: String, val canContinue: Boolean) : Message()
	data class PhoneNumberChanged(val phoneNumber: String, val canContinue: Boolean) : Message()
}
