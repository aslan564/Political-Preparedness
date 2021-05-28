package com.example.android.politicalpreparedness.sharedManager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.util.prefs.AbstractPreferences

object SharedPreferenceManager {
    private const val SHARED_PREFERENCES_FILE_KEY =
        "com.example.android.politicalpreparedness.sharedManager"
    private lateinit var instance: SharedPreferenceManager
    private lateinit var sharedPreferences: SharedPreferences
    private val IS_LOGIN = Pair<String, Boolean>("is_login", false)

    fun instance(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    //SharedPreferences variables getters/setters
    var isLogin: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGIN.first, IS_LOGIN.second)
        set(value) = sharedPreferences.edit {
            it.putBoolean(IS_LOGIN.first, value)
        }

}