package com.example.dynamiclocale

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

/**
 * 앱의 로케일을 관리하는 헬퍼 클래스
 */
object LocaleHelper {
    
    /**
     * 앱의 오버라이드 로케일을 설정합니다.
     * Android 13 이상에서는 LocaleManager를 사용하고, 그 이하 버전에서는 AppCompatDelegate를 사용합니다.
     * 
     * @param context 컨텍스트
     * @param locale 설정할 로케일
     */
    fun setOverrideLocale(context: Context, locale: Locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            setLocaleWithLocaleManager(context, locale)
        } else {
            Log.d("LocaleHelper", "locale=$locale")
            setLocaleWithAppCompat(locale)
        }
    }
    
    /**
     * Android 13 이상에서 LocaleManager를 사용하여 로케일을 설정합니다.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setLocaleWithLocaleManager(context: Context, locale: Locale) {
        val localeManager = context.getSystemService(Context.LOCALE_SERVICE) as LocaleManager
        localeManager.applicationLocales = LocaleList(locale)
    }
    
    /**
     * Android 13 이전 버전에서 AppCompatDelegate를 사용하여 로케일을 설정합니다.
     */
    private fun setLocaleWithAppCompat(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale))
    }

    /**
     * 현재 앱에 설정된 로케일을 반환합니다.
     */
    fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getLocaleWithLocaleManager(context)
        } else {
            getLocaleWithAppCompat()
        }
    }
    
    /**
     * Android 13 이상에서 LocaleManager를 사용하여 현재 로케일을 가져옵니다.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getLocaleWithLocaleManager(context: Context): Locale {
        val localeManager = context.getSystemService(Context.LOCALE_SERVICE) as LocaleManager
        return if (localeManager.applicationLocales.isEmpty) {
            Locale.getDefault()
        } else {
            localeManager.applicationLocales[0]
        }
    }
    
    /**
     * Android 13 이전 버전에서 AppCompatDelegate를 사용하여 현재 로케일을 가져옵니다.
     */
    private fun getLocaleWithAppCompat(): Locale {
        val localeList = AppCompatDelegate.getApplicationLocales()
        return if (localeList.isEmpty) {
            Locale.getDefault()
        } else {
            localeList[0]!!
        }
    }
}
