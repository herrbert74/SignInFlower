package com.zsoltbertalan.signinflower.ui

import com.zsoltbertalan.signinflower.data.local.Prefs
import com.zsoltbertalan.signinflower.data.network.SignInFlowerAccessor
import com.zsoltbertalan.signinflower.data.network.SignInFlowerService
import com.zsoltbertalan.signinflower.data.network.dto.StartDto

val defaultSignInFlowerService = object : SignInFlowerService {
	override suspend fun getStart(): List<StartDto> {
		TODO("Not yet implemented")
	}
}

val defaultPrefs = object : Prefs {
	override fun saveUserFirstName(firstName: String) {
		TODO("Not yet implemented")
	}

	override fun saveUserLastName(lastName: String) {
		TODO("Not yet implemented")
	}

	override fun saveUserEmail(email: String) {
		TODO("Not yet implemented")
	}

	override fun saveUserPassword(password: String) {
		TODO("Not yet implemented")
	}

	override fun saveUserPhoneNumber(phoneNumber: String) {
		TODO("Not yet implemented")
	}

	override fun saveUserPin(pin: String) {
		TODO("Not yet implemented")
	}

	override fun saveUserIsSignedIn(isUserSignedIn: Boolean) {
		TODO("Not yet implemented")
	}

	override fun deleteAllUserdata() {
		TODO("Not yet implemented")
	}

	override fun getUserFirstName(): String {
		TODO("Not yet implemented")
	}

	override fun getUserLastName(): String {
		TODO("Not yet implemented")
	}

	override fun getUserEmail(): String {
		TODO("Not yet implemented")
	}

	override fun getUserPassword(): String {
		TODO("Not yet implemented")
	}

	override fun getUserPhoneNumber(): String {
		TODO("Not yet implemented")
	}

	override fun getUserPin(): String {
		TODO("Not yet implemented")
	}

	override fun isUserSignedIn(): Boolean {
		TODO("Not yet implemented")
	}

}

fun defaultSingInFlowerRepository() = SignInFlowerAccessor(defaultPrefs)
