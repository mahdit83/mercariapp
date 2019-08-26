package com.mercari.data.networking.manager

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.mercari.data.BuildConfig

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationManager @Inject
constructor(context: Context) {

    private var accessToken = ""
    private var refreshToken = ""


    internal var sharedPreferences: SharedPreferences

    init {

        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    fun updateAccessToken(accessToken: String) {
        this.accessToken = accessToken

        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, accessToken).apply()
    }

    fun getAccessToken(): String? {
        if (TextUtils.isEmpty(accessToken)) {
            accessToken = sharedPreferences.getString(KEY_AUTH_TOKEN, "")
        }
        return accessToken

    }

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, accessToken).apply()
    }


    fun getRefreshToken(): String {
        if (TextUtils.isEmpty(refreshToken)) {
            sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
        }
        return refreshToken
    }

    companion object {

        /*
	In this project  do not supports Oauth2 and in other part
	 */
        const val KEY_AUTH_TOKEN = "auth_token"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_PHONE_NUMBER = "phone_number"
        const val PREFERENCES = BuildConfig.PREFRENCES
    }

}
