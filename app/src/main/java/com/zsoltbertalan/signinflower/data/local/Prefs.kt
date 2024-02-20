package com.zsoltbertalan.signinflower.data.local

interface Prefs {

	fun saveUserFirstName(firstName: String)
	fun saveUserLastName(lastName: String)
	fun saveUserEmail(email: String)
	fun saveUserPassword(password: String)
	fun saveUserPhoneNumber(phoneNumber: String)
	fun saveUserPin(pin: String)
	fun saveUserIsSignedIn(isUserSignedIn: Boolean)

	fun deleteAllUserdata()

	fun getUserFirstName(): String
	fun getUserLastName(): String
	fun getUserEmail(): String
	fun getUserPassword(): String
	fun getUserPhoneNumber(): String
	fun getUserPin(): String

	fun isUserSignedIn(): Boolean

}