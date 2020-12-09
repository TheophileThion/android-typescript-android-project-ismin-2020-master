package com.ismin.android

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class FavoriteFilms {
    companion object {
        var favs = ArrayList<String>()
        fun loadFavoriteFilms(activity: Activity) {
            val sp: SharedPreferences = activity.getSharedPreferences("favs", AppCompatActivity.MODE_PRIVATE)
            if (sp.contains("favs")) {
                val arr = sp.getStringSet("favs", setOf())!!.toTypedArray()
                favs = ArrayList()
                arr.forEach { title ->
                    run {
                        favs.add(title)
                    }
                }
            }
        }

        fun saveFavoriteFilms(activity: Activity) {
            val sp: SharedPreferences = activity.getSharedPreferences("favs", AppCompatActivity.MODE_PRIVATE)
            val ed = sp.edit()
            ed.putStringSet("favs", favs.toSet())
            ed.apply()
        }
    }
}