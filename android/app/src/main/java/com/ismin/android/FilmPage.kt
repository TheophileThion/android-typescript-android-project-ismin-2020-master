package com.ismin.android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmPage : AppCompatActivity() {
    var externalLink="https://www.imdb.com/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://imdb.thionman.cleverapps.io")
        .build()

    private val filmService: FilmService = retrofit.create(FilmService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_page)
        val rootView: View = window.decorView.rootView
        val extras = intent.extras
        if (extras == null) {
            finish()
        }
        val title: String = extras?.getString("film").toString()

        filmService.getFilm(title).enqueue(object : Callback<Film> {
            @RequiresApi(Build.VERSION_CODES.M)
            @SuppressLint("SetTextI18n", "CutPasteId")
            override fun onResponse(
                call: Call<Film>,
                response: Response<Film>
            ) {
                val film = response.body()
                if (film != null) {
                    Picasso.get().load(film.cover)
                        .into(rootView.findViewById<ImageView>(R.id.detail_cover))
                    rootView.findViewById<TextView>(R.id.detail_rating).text =
                        (film.aggregated_rating*10).toInt().toString()
                    val rating = (film.aggregated_rating*10)
                    var ratingColor = getColor(R.color.ratingGrey)
                    if (rating>0){
                        ratingColor=getColor(R.color.ratingRed)
                    }
                    if (rating>50) {
                        ratingColor = getColor(R.color.ratingOrange)
                    }
                    if (rating>75) {
                        ratingColor = getColor(R.color.ratingGreen)
                    }
                    if (rating>90) {
                        ratingColor = getColor(R.color.ratingBlue)
                    }

                    rootView.findViewById<TextView>(R.id.detail_rating).backgroundTintList = ColorStateList.valueOf(ratingColor)
                    rootView.findViewById<TextView>(R.id.detail_members).text =
                        film.members.toString()
                    rootView.findViewById<TextView>(R.id.detail_title).text = film.title
                    rootView.findViewById<TextView>(R.id.detail_info).text =
                        film.genres.joinToString(", ") + " · " + film.first_release_date + " · " + film.runtime.toString() + " minutes"
                    rootView.findViewById<TextView>(R.id.detail_authors).text = getString(R.string.authors)+": "+film.authors.joinToString(" · ")
                    rootView.findViewById<TextView>(R.id.detail_casting).text = getString(R.string.casting)+": "+film.casting.joinToString(" · ")
                    rootView.findViewById<TextView>(R.id.detail_summary).text = film.summary
                    rootView.findViewById<TextView>(R.id.detail_keywords).text = film.keywords.joinToString(" · ")
                    externalLink = film.url
                    rootView.findViewById<CheckBox>(R.id.detail_fav).isChecked = title in FavoriteFilms.favs
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                displayErrorToast(t)
            }
        })
    }

    fun setFavorite(view: View) {
        val checkBox = view as CheckBox
        if (checkBox.isChecked) {
            FavoriteFilms.favs.add((view.parent as View).findViewById<TextView>(R.id.detail_title).text.toString())
        } else {
            FavoriteFilms.favs.remove((view.parent as View).findViewById<TextView>(R.id.detail_title).text.toString())
        }
        FavoriteFilms.saveFavoriteFilms(this)
    }

    fun openUrl(view:View) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(externalLink)
        startActivity(openURL)
    }
    fun deleteFilm(view:View){
        filmService.deleteFilm(window.decorView.rootView.findViewById<TextView>(R.id.detail_title).text.toString()).enqueue(object : Callback<Film> {
            override fun onResponse(
                call: Call<Film>,
                response: Response<Film>
            ) {
                finish()
            }
            override fun onFailure(call: Call<Film>, t: Throwable) {
                displayErrorToast(t)
            }
        })
    }
    private fun displayErrorToast(t: Throwable) {
        Toast.makeText(
            applicationContext,
            "Network error ${t.localizedMessage}",
            Toast.LENGTH_LONG
        ).show()
    }

}