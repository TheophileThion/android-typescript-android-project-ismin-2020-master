package com.ismin.android

import retrofit2.Call
import retrofit2.http.*

interface FilmService {

    @GET("films")
    fun getFilms(
        @Query("author") author: String = "",
        @Query("search") search: String = "",
        @Query("genre") genre: String = "",
        @Query("sort-by") sortby: String = "",
        @Query("asc") asc: String = "",
        @Query("limit") limit: Int= 0
    ): Call<ArrayList<Film>>

    @GET("films/{film}")
    fun getFilm(@Path("film") title : String): Call<Film>

    @POST("films")
    fun createFilm(@Body() film: Film): Call<Film>

    @DELETE("films/{film}")
    fun deleteFilm(@Path("film") title : String): Call<Film>
}