package com.ismin.android

import java.io.Serializable

class Films : Serializable {
    private val storage = ArrayList<Film>()
    private val genres = ArrayList<String>()

    fun addFilm(film: Film) {
        this.storage.add(film)
    }

    fun getFilms(title: String): ArrayList<Film> {
        return ArrayList(this.storage.filter {film -> film.title == title})
    }

    fun getAllFilms(): List<Film> {
        return storage
    }
    fun getgenres ():ArrayList<String>{
        return genres
    }
    fun getTotalNumberOfFilms(): Int {
        return this.storage.size
    }
}
