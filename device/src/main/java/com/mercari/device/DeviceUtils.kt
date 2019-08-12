package com.mercari.device

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log


class DeviceUtils {

    val deviceName: String
        get() {
            return try {
                Build.DEVICE
            } catch (ex: Throwable) {
                ""
            }

        }

    val product: String
        get() {
            return try {
                Build.PRODUCT
            } catch (ex: Throwable) {
                ""
            }
        }

    val manufacturer: String
        get() {
            return try {
                Build.MANUFACTURER
            } catch (ex: Throwable) {
                ""
            }

        }

    val buildType: String
        get() {
            return try {
                Build.TYPE
            } catch (ex: Throwable) {
                ""
            }

        }

    fun getDisplayDensity(context: Context): Float {
        try {
            return context.resources.displayMetrics.density
        } catch (ex: Throwable) {
            return 0f
        }

    }

    companion object {
        val WIFI = "WIFI"
        val GPRS = "GPRS"
        private val TAG = "Mercai"

        fun getVersionCode(context: Context): Int {
            return try {
                context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            } catch (ex: Throwable) {
                0
            }

        }


        val deviceModel: String
            get() {
                return try {
                    val deviceModel: String? = Build.MODEL
                    deviceModel ?: ""
                } catch (ex: Throwable) {
                    ""
                }

            }


        @SuppressLint("HardwareIds")
        fun getAndroidId(context: Context): String {
            return try {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            } catch (ex: Throwable) {
                Log.e(TAG, "DeviceUtils => (deviceId)")
                ""
            }

        }

        val brand: String
            get() {
                return try {
                    Build.BRAND
                } catch (ex: Throwable) {
                    ""
                }

            }
    }


}
