package com.zsoltbertalan.signinflower.root

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.zsoltbertalan.signinflower.ui.main.MainComp
import com.zsoltbertalan.signinflower.ui.onboarding.confirmpin.ConfirmPinComp
import com.zsoltbertalan.signinflower.ui.onboarding.credentials.CredentialsComp
import com.zsoltbertalan.signinflower.ui.onboarding.newpin.NewPinComp
import com.zsoltbertalan.signinflower.ui.onboarding.personalinfo.PersonalInfoComp
import com.zsoltbertalan.signinflower.ui.onboarding.terms.TermsComp
import com.zsoltbertalan.signinflower.ui.onboarding.welcome.WelcomeComp
import com.zsoltbertalan.signinflower.ui.start.StartComp

val navigation = StackNavigation<Configuration>()

internal fun onStartOutput(output: StartComp.Output): Unit = when (output) {
	is StartComp.Output.PushWelcome -> navigation.replaceCurrent(Configuration.Welcome)
	is StartComp.Output.PushMain -> navigation.replaceCurrent(Configuration.Main)
}

internal fun onWelcomeOutput(output: WelcomeComp.Output): Unit = when (output) {
	WelcomeComp.Output.Forward -> navigation.push(Configuration.Terms)
}

internal fun onTermsOutput(output: TermsComp.Output): Unit = when (output) {
	TermsComp.Output.Back -> navigation.pop()
	TermsComp.Output.Forward -> navigation.push(Configuration.Credentials)
}

internal fun onCredentialsOutput(output: CredentialsComp.Output): Unit = when (output) {
	CredentialsComp.Output.Back -> navigation.pop()
	CredentialsComp.Output.Forward -> navigation.push(Configuration.PersonalInfo)
}

internal fun onPersonalInfoOutput(output: PersonalInfoComp.Output): Unit = when (output) {
	PersonalInfoComp.Output.Back -> navigation.pop()
	PersonalInfoComp.Output.Forward -> navigation.push(Configuration.NewPin)
}

internal fun onNewPinOutput(output: NewPinComp.Output): Unit = when (output) {
	NewPinComp.Output.Back -> navigation.pop()
	NewPinComp.Output.Forward -> navigation.push(Configuration.ConfirmPin)
}

internal fun onConfirmPinOutput(output: ConfirmPinComp.Output): Unit = when (output) {
	ConfirmPinComp.Output.Back -> navigation.pop()
	ConfirmPinComp.Output.Forward -> navigation.push(Configuration.Main)
}

internal fun onMainOutput(output: MainComp.Output): Unit = when (output) {
	MainComp.Output.SignOut -> navigation.replaceAll(Configuration.Welcome)
}
