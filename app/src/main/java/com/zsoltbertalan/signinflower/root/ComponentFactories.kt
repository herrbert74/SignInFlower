package com.zsoltbertalan.signinflower.root

import com.zsoltbertalan.signinflower.data.SignInFlowerRepository
import com.zsoltbertalan.signinflower.ui.onboarding.confirmpin.ConfirmPinComponent
import com.zsoltbertalan.signinflower.ui.onboarding.credentials.CredentialsComponent
import com.zsoltbertalan.signinflower.ui.onboarding.newpin.NewPinComponent
import com.zsoltbertalan.signinflower.ui.onboarding.personalinfo.PersonalInfoComponent
import com.zsoltbertalan.signinflower.ui.onboarding.terms.TermsComponent
import com.zsoltbertalan.signinflower.ui.main.MainComponent
import com.zsoltbertalan.signinflower.ui.onboarding.welcome.WelcomeComponent
import com.zsoltbertalan.signinflower.ui.start.StartComponent
import kotlinx.coroutines.CoroutineDispatcher

/**
 * These are higher order functions with common parameters used in the RootComponent,
 * that return functions, that create the Decompose feature components from feature specific parameters.
 */

internal fun createStartFactory(
	signInFlowerRepository: SignInFlowerRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreateStartComp = { childContext, output ->
	StartComponent(
		componentContext = childContext,
		mainContext,
		ioContext,
		output = output,
		signInFlowerRepository = signInFlowerRepository
	)
}

internal fun createWelcomeFactory(mainContext: CoroutineDispatcher): CreateWelcomeComp =
	{ childContext, finishHandler, output ->
		WelcomeComponent(
			componentContext = childContext,
			mainContext,
			output = output,
			finishHandler = finishHandler,
		)
	}

internal fun createTermsFactory(mainContext: CoroutineDispatcher): CreateTermsComp = { childContext, output ->
	TermsComponent(
		componentContext = childContext,
		mainContext,
		output = output,
	)
}

internal fun createCredentialsFactory(
	signInFlowerRepository: SignInFlowerRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreateCredentialsComp = { childContext, output ->
	CredentialsComponent(
		componentContext = childContext,
		signInFlowerRepository = signInFlowerRepository,
		mainContext,
		ioContext,
		output = output,
	)
}

internal fun createPersonalInfoFactory(
	signInFlowerRepository: SignInFlowerRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreatePersonalInfoComp = { childContext, output ->
	PersonalInfoComponent(
		componentContext = childContext,
		mainContext,
		ioContext,
		signInFlowerRepository,
		output = output,
	)
}

internal fun createNewPinFactory(
	signInFlowerRepository: SignInFlowerRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreateNewPinComp = { childContext, output ->
	NewPinComponent(
		componentContext = childContext,
		signInFlowerRepository = signInFlowerRepository,
		mainContext,
		ioContext,
		output = output,
	)
}

internal fun createConfirmPinFactory(
	signInFlowerRepository: SignInFlowerRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreateConfirmPinComp = { childContext, output ->
	ConfirmPinComponent(
		componentContext = childContext,
		signInFlowerRepository = signInFlowerRepository,
		mainContext,
		ioContext,
		output = output,
	)
}

internal fun createMainFactory(
	signInFlowerRepository: SignInFlowerRepository,
	mainContext: CoroutineDispatcher,
	ioContext: CoroutineDispatcher,
): CreateMainComp = { childContext, finishHandler, output ->
	MainComponent(
		componentContext = childContext,
		signInFlowerRepository = signInFlowerRepository,
		mainContext,
		ioContext,
		output = output,
		finishHandler = finishHandler
	)
}
