package com.mani.idonethis

import android.content.SharedPreferences

class UserSharedPreference(private val sharedPreferences: SharedPreferences) {

    fun hasUserOnBoard(): Boolean {
        val userId = sharedPreferences.getString(USER_ID_KEY, null)
        return userId != null
    }

    fun addUser(userId: String) {
        sharedPreferences
            .edit()
            .putString(USER_ID_KEY, userId)
            .apply()
    }

    fun getUserId(): String {
        return sharedPreferences.getString(USER_ID_KEY, "")
    }

    fun removeUser() {
        sharedPreferences
            .edit()
            .remove(USER_ID_KEY)
            .apply()
    }

    companion object {
        private const val USER_ID_KEY = "userId"
    }
}
