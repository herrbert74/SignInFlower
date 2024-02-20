package com.zsoltbertalan.signinflower.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.zsoltbertalan.signinflower.design.SignInFlowerTheme
import com.zsoltbertalan.signinflower.ui.onboarding.confirmpin.ConfirmPinScreen
import com.zsoltbertalan.signinflower.ui.onboarding.credentials.CredentialsScreen
import com.zsoltbertalan.signinflower.ui.onboarding.newpin.NewPinScreen
import com.zsoltbertalan.signinflower.ui.onboarding.personalinfo.PersonalInfoScreen
import com.zsoltbertalan.signinflower.ui.onboarding.terms.TermsScreen
import com.zsoltbertalan.signinflower.ui.main.MainScreen
import com.zsoltbertalan.signinflower.ui.onboarding.welcome.WelcomeScreen
import com.zsoltbertalan.signinflower.ui.start.StartScreen

@Composable
fun SignInFlowerRootContent(component: SignInFlowerRootComp) {

	val stack = component.childStackValue

	SignInFlowerTheme {
		Children(stack = stack, animation = stackAnimation(scale())) {
			when (val child = it.instance) {
				is SignInFlowerChild.Start -> StartScreen(child.component)
				is SignInFlowerChild.Welcome -> WelcomeScreen(child.component)
				is SignInFlowerChild.Terms -> TermsScreen(child.component)
				is SignInFlowerChild.Credentials -> CredentialsScreen(child.component)
				is SignInFlowerChild.PersonalInfo -> PersonalInfoScreen(child.component)
				is SignInFlowerChild.NewPin -> NewPinScreen(child.component)
				is SignInFlowerChild.ConfirmPin -> ConfirmPinScreen(child.component)
				is SignInFlowerChild.Main -> MainScreen(child.component)
			}
		}
	}

}
