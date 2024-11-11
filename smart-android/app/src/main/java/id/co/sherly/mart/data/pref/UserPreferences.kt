package id.co.sherly.mart.data.pref

import android.content.SharedPreferences
import id.co.sherly.mart.data.pref.delegates.booleanPreference
import id.co.sherly.mart.data.pref.delegates.nullableStringPreference
import id.co.sherly.mart.di.UserPrefs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    @UserPrefs preferences: SharedPreferences
) {

    var isBiometricEnable by preferences.booleanPreference(IS_BIOMETRIC_ENABLE, false)
    var name by preferences.nullableStringPreference(NAME)
    var username by preferences.nullableStringPreference(USERNAME)
    var isLoggedIn by preferences.booleanPreference(IS_LOGGED_IN, false)
}

private const val NAME = "NAME"
private const val USERNAME = "USERNAME"
private const val IS_LOGGED_IN = "IS_LOGGED_IN"
private const val IS_BIOMETRIC_ENABLE = "IS_BIOMETRIC_ENABLE"
