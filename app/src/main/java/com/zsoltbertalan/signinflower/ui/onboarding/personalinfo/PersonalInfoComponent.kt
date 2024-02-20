package com.zsoltbertalan.signinflower.ui.onboarding.personalinfo

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

interface PersonalInfoComp {

	fun onBackClicked()

	fun firstNameChanged(email:String)

	fun lastNameChanged(password:String)

	fun phoneNumberChanged(phoneNumber:String)

	fun onForwardClicked()

	sealed class Output {
		data object Back : Output()
		data object Forward : Output()
	}

	val state: Value<State>

}

class PersonalInfoComponent(
	componentContext: ComponentContext,
	@MainDispatcher private val mainDispatcher: CoroutineDispatcher,
	@IoDispatcher ioContext: CoroutineDispatcher,
	signInFlowerRepository: SignInFlowerRepository,
	private val output: FlowCollector<PersonalInfoComp.Output>,
) : PersonalInfoComp, ComponentContext by componentContext {

	private var personalInfoStore = personalInfoStore(
		LoggingStoreFactory(DefaultStoreFactory()),
		mainDispatcher,
		ioContext,
		signInFlowerRepository
	)

	override fun onBackClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(PersonalInfoComp.Output.Back)
		}
	}

	override fun firstNameChanged(email: String) {
		personalInfoStore.accept(Intent.FirstNameChanged(email))
	}

	override fun lastNameChanged(password: String) {
		personalInfoStore.accept(Intent.LastNameChanged(password))
	}

	override fun phoneNumberChanged(phoneNumber: String) {
		personalInfoStore.accept(Intent.PhoneNumberChanged(phoneNumber))
	}

	override fun onForwardClicked() {
		CoroutineScope(mainDispatcher).launch {
			output.emit(PersonalInfoComp.Output.Forward)
		}
	}

	override val state: Value<State>
		get() = personalInfoStore.asValue()

}
