package com.example.android.siv.utilities

import android.content.Context
import android.text.TextUtils

import android.content.SharedPreferences

object ReturnSharedPreference {
    fun get(context: Context): SharedPreferences {
        return context.applicationContext
            .getSharedPreferences("vacina_app", Context.MODE_PRIVATE)
    }

    fun GetUserID(context: Context): String {
        val preferences = get(context)
        val id = preferences.getInt("id_user", 0)


        return id.toString()
    }

    fun SaveUserID(context: Context, id_user : Int) {
        val preferences = get(context)
        var editor = preferences.edit()
        editor.putInt("id_user", id_user)

        editor.commit()
    }
}