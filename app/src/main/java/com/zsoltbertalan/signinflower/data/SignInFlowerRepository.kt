package com.zsoltbertalan.signinflower.data

interface SignInFlowerRepository {

	fun saveUserFirstName(firstName: String)
	fun saveUserLastName(lastName: String)
	fun saveUserEmail(email: String)
	fun saveUserPassword(password: String)
	fun saveUserPhoneNumber(phoneNumber: String)
	fun saveUserPin(pin: String)

	/**
	 * Unused. Simplified by simply using PIN to determine logged in status.
	 */
	fun saveUserIsSignedIn(isUserSignedIn: Boolean)

	fun deleteAllUserData()

	fun getUserFirstName(): String
	fun getUserLastName(): String
	fun getUserEmail(): String
	fun getUserPassword(): String
	fun getUserPhoneNumber(): String
	fun getUserPin(): String

	/**
	 * Simply using PIN to determine logged in status.
	 */
	fun isUserSignedIn(): Boolean

}
