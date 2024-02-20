package com.zsoltbertalan.signinflower.root

import com.zsoltbertalan.signinflower.ui.onboarding.confirmpin.ConfirmPinComp
import com.zsoltbertalan.signinflower.ui.onboarding.credentials.CredentialsComp
import com.zsoltbertalan.signinflower.ui.onboarding.newpin.NewPinComp
import com.zsoltbertalan.signinflower.ui.onboarding.personalinfo.PersonalInfoComp
import com.zsoltbertalan.signinflower.ui.onboarding.terms.TermsComp
import com.zsoltbertalan.signinflower.ui.main.MainComp
import com.zsoltbertalan.signinflower.ui.onboarding.welcome.WelcomeComp
import com.zsoltbertalan.signinflower.ui.start.StartComp

sealed class SignInFlowerChild {
	data class Start(val component: StartComp) : SignInFlowerChild()
	data class Welcome(val component: WelcomeComp) : SignInFlowerChild()
	data class Terms(val component: TermsComp) : SignInFlowerChild()
	data class Credentials(val component: CredentialsComp) : SignInFlowerChild()
	data class PersonalInfo(val component: PersonalInfoComp) : SignInFlowerChild()
	data class NewPin(val component: NewPinComp) : SignInFlowerChild()
	data class ConfirmPin(val component: ConfirmPinComp) : SignInFlowerChild()
	data class Main(val component: MainComp) : SignInFlowerChild()
}
