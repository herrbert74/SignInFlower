package com.zsoltbertalan.signinflower.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.zsoltbertalan.signinflower.domain.api.SignInFlowerRepository
import com.zsoltbertalan.signinflower.di.IoDispatcher
import com.zsoltbertalan.signinflower.di.MainDispatcher
import com.zsoltbertalan.signinflower.ui.onboarding.confirmpin.ConfirmPinComp
import com.zsoltbertalan.signinflower.ui.onboarding.credentials.CredentialsComp
import com.zsoltbertalan.signinflower.ui.onboarding.newpin.NewPinComp
import com.zsoltbertalan.signinflower.ui.onboarding.personalinfo.PersonalInfoComp
import com.zsoltbertalan.signinflower.ui.onboarding.terms.TermsComp
import com.zsoltbertalan.signinflower.ui.main.MainComp
import com.zsoltbertalan.signinflower.ui.onboarding.welcome.WelcomeComp
import com.zsoltbertalan.signinflower.ui.start.StartComp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector

typealias CreateStartComp = (ComponentContext, FlowCollector<StartComp.Output>) -> StartComp
typealias CreateWelcomeComp = (ComponentContext, () -> Unit, FlowCollector<WelcomeComp.Output>) -> WelcomeComp
typealias CreateTermsComp = (ComponentContext, FlowCollector<TermsComp.Output>) -> TermsComp
typealias CreateCredentialsComp = (ComponentContext, FlowCollector<CredentialsComp.Output>) -> CredentialsComp
typealias CreatePersonalInfoComp = (ComponentContext, FlowCollector<PersonalInfoComp.Output>) -> PersonalInfoComp
typealias CreateNewPinComp = (ComponentContext, FlowCollector<NewPinComp.Output>) -> NewPinComp
typealias CreateConfirmPinComp = (ComponentContext, FlowCollector<ConfirmPinComp.Output>) -> ConfirmPinComp
typealias CreateMainComp = (ComponentContext, () -> Unit, FlowCollector<MainComp.Output>) -> MainComp

interface SignInFlowerRootComp {
	val childStackValue: Value<ChildStack<*, SignInFlowerChild>>
}

class SignInFlowerRootComponent internal constructor(
	componentContext: ComponentContext,
	private val finishHandler: () -> Unit,
	private val createStartComp: CreateStartComp,
	private val createWelcomeComp: CreateWelcomeComp,
	private val createTermsComp: CreateTermsComp,
	private val createCredentialsComp: CreateCredentialsComp,
	private val createPersonalInfoComp: CreatePersonalInfoComp,
	private val createNewPinComp: CreateNewPinComp,
	private val createConfirmPinComp: CreateConfirmPinComp,
	private val createMainComp: CreateMainComp,
) : SignInFlowerRootComp,
	ComponentContext by componentContext {

	constructor(
		componentContext: ComponentContext,
		@MainDispatcher mainContext: CoroutineDispatcher,
		@IoDispatcher ioContext: CoroutineDispatcher,
		signInFlowerRepository: SignInFlowerRepository,
		finishHandler: () -> Unit,
	) : this(
		componentContext = componentContext,
		finishHandler,
		createStartComp = createStartFactory(signInFlowerRepository, mainContext, ioContext),
		createWelcomeComp = createWelcomeFactory(mainContext),
		createTermsComp = createTermsFactory(mainContext),
		createCredentialsComp = createCredentialsFactory(signInFlowerRepository, mainContext, ioContext),
		createPersonalInfoComp = createPersonalInfoFactory(signInFlowerRepository, mainContext, ioContext),
		createNewPinComp = createNewPinFactory(signInFlowerRepository, mainContext, ioContext),
		createConfirmPinComp = createConfirmPinFactory(signInFlowerRepository, mainContext, ioContext),
		createMainComp = createMainFactory(signInFlowerRepository, mainContext, ioContext),
	)

	private val stack = childStack(
		source = navigation,
		initialStack = { listOf(Configuration.Start) },
		handleBackButton = true,
		childFactory = ::createChild
	)

	override val childStackValue = stack

	private fun createChild(configuration: Configuration, componentContext: ComponentContext): SignInFlowerChild =
		when (configuration) {

			is Configuration.Start -> SignInFlowerChild.Start(
				createStartComp(
					componentContext.childContext(key = "StartComponent"),
					FlowCollector(::onStartOutput)
				)
			)

			is Configuration.Welcome -> SignInFlowerChild.Welcome(
				createWelcomeComp(
					componentContext.childContext(key = "WelcomeComponent"),
					finishHandler,
					FlowCollector(::onWelcomeOutput)
				)
			)

			is Configuration.Terms -> SignInFlowerChild.Terms(
				createTermsComp(
					componentContext.childContext(key = "TermsComponent"),
					FlowCollector(::onTermsOutput)
				)
			)

			is Configuration.Credentials -> SignInFlowerChild.Credentials(
				createCredentialsComp(
					componentContext.childContext(key = "CredentialsComponent"),
					FlowCollector(::onCredentialsOutput)
				)
			)

			is Configuration.PersonalInfo -> SignInFlowerChild.PersonalInfo(
				createPersonalInfoComp(
					componentContext.childContext(key = "PersonalInfoComponent"),
					FlowCollector(::onPersonalInfoOutput)
				)
			)

			is Configuration.NewPin -> SignInFlowerChild.NewPin(
				createNewPinComp(
					componentContext.childContext(key = "NewPinComponent"),
					FlowCollector(::onNewPinOutput)
				)
			)

			is Configuration.ConfirmPin -> SignInFlowerChild.ConfirmPin(
				createConfirmPinComp(
					componentContext.childContext(key = "ConfirmPinComponent"),
					FlowCollector(::onConfirmPinOutput)
				)
			)

			is Configuration.Main -> SignInFlowerChild.Main(
				createMainComp(
					componentContext.childContext(key = "MainComponent"),
					finishHandler,
					FlowCollector(::onMainOutput)
				)
			)
		}

}
