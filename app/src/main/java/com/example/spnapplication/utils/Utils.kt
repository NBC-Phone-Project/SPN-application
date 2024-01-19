package com.example.spnapplication.utils

import android.app.Activity
import android.content.Context
import com.example.spnapplication.MyInfo
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException

object Utils {

    private const val PREFS_NAME = "pref"
    private const val USER_INFO_KEY = "userInfo"

    fun savePrefUser(context: Context, item: MyInfo) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = GsonBuilder().create()
        editor.putString(USER_INFO_KEY, gson.toJson(item))
        editor.apply()
    }

    fun getPrefUser(context: Context): MyInfo? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE)
        val gson = GsonBuilder().create()
        val json = prefs.getString(USER_INFO_KEY, null) ?: return null
        return try {
            gson.fromJson(json, MyInfo::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }
}