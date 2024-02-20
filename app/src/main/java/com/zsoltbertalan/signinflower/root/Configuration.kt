package com.zsoltbertalan.signinflower.root

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

sealed class Configuration : Parcelable {

	@Parcelize
	data object Start : Configuration()

	@Parcelize
	data object Welcome : Configuration()

	@Parcelize
	data object Terms : Configuration()

	@Parcelize
	data object Credentials : Configuration()

	@Parcelize
	data object PersonalInfo : Configuration()

	@Parcelize
	data object NewPin : Configuration()

	@Parcelize
	data object ConfirmPin : Configuration()

	@Parcelize
	data object Main : Configuration()

}
