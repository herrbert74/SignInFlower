package com.zsoltbertalan.signinflower.data.local

import android.content.SharedPreferences

const val SIGN_IN_FLOWER_PREFS = "SignInFlowerPrefs"
const val PREF_FIRST_NAME = "pref_first_name"
const val PREF_LAST_NAME = "pref_last_name"
const val PREF_EMAIL = "pref_email"
const val PREF_PASSWORD = "pref_password"
const val PREF_PHONE_NUMBER = "pref_phone_number"
const val PREF_PIN = "pref_pin"
const val PREF_IS_USER_SIGNED_IN = "pref_pin"

class PrefsAccessor internal constructor(private val sharedPreferences: SharedPreferences) : Prefs {

	override fun saveUserFirstName(firstName: String) = sharedPreferences.putString(PREF_FIRST_NAME, firstName)

	override fun saveUserLastName(lastName: String) = sharedPreferences.putString(PREF_LAST_NAME, lastName)

	override fun saveUserEmail(email: String) = sharedPreferences.putString(PREF_EMAIL, email)

	override fun saveUserPassword(password: String) = sharedPreferences.putString(PREF_PASSWORD, password)

	override fun saveUserPhoneNumber(phoneNumber: String) =
		sharedPreferences.putString(PREF_PHONE_NUMBER, phoneNumber)

	override fun saveUserPin(pin: String) = sharedPreferences.putString(PREF_PIN, pin)

	override fun saveUserIsSignedIn(isUserSignedIn: Boolean) =
		sharedPreferences.putBoolean(PREF_IS_USER_SIGNED_IN, isUserSignedIn)

	override fun deleteAllUserdata() {
		val editor = sharedPreferences.edit()
		editor.clear().apply()
	}

	override fun getUserFirstName(): String = sharedPreferences.getString(PREF_FIRST_NAME, "") ?: ""

	override fun getUserLastName(): String = sharedPreferences.getString(PREF_LAST_NAME, "") ?: ""

	override fun getUserEmail(): String = sharedPreferences.getString(PREF_EMAIL, "") ?: ""

	override fun getUserPassword(): String = sharedPreferences.getString(PREF_PASSWORD, "") ?: ""

	override fun getUserPhoneNumber(): String = sharedPreferences.getString(PREF_PHONE_NUMBER, "") ?: ""

	override fun getUserPin(): String = sharedPreferences.getString(PREF_PIN, "") ?: ""

	override fun isUserSignedIn(): Boolean = getUserPin().isNotEmpty()

}

fun SharedPreferences.putString(key: String, string: String) {
	with(edit()) {
		putString(key, string)
		apply()
	}
}

fun SharedPreferences.putBoolean(key: String, boolean: Boolean) {
	with(edit()) {
		putBoolean(key, boolean)
		apply()
	}
}
