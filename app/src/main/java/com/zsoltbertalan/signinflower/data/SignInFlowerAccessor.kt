package com.zsoltbertalan.signinflower.data

import com.zsoltbertalan.signinflower.data.SignInFlowerRepository
import com.zsoltbertalan.signinflower.data.local.Prefs
import javax.inject.Singleton

@Singleton
class SignInFlowerAccessor(private val prefs: Prefs) : SignInFlowerRepository {

	override fun saveUserFirstName(firstName: String) = prefs.saveUserFirstName(firstName)

	override fun saveUserLastName(lastName: String) = prefs.saveUserLastName(lastName)

	override fun saveUserEmail(email: String) = prefs.saveUserEmail(email)

	override fun saveUserPassword(password: String) = prefs.saveUserPassword(password)

	override fun saveUserPhoneNumber(phoneNumber: String)= prefs.saveUserPhoneNumber(phoneNumber)

	override fun saveUserPin(pin: String) =prefs.saveUserPin(pin)

	override fun saveUserIsSignedIn(isUserSignedIn: Boolean) = prefs.saveUserIsSignedIn(isUserSignedIn)

	override fun deleteAllUserData() {
		prefs.deleteAllUserdata()
	}
	override fun getUserFirstName(): String  = prefs.getUserFirstName()

	override fun getUserLastName(): String = prefs.getUserLastName()

	override fun getUserEmail(): String =prefs.getUserEmail()

	override fun getUserPassword(): String =prefs.getUserPassword()

	override fun getUserPhoneNumber(): String = prefs.getUserPhoneNumber()

	override fun getUserPin(): String = prefs.getUserPin()

	override fun isUserSignedIn(): Boolean= prefs.isUserSignedIn()

}
