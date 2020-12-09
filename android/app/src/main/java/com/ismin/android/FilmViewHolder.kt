package com.ismin.android

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var coverImage: ImageView = itemView.findViewById(R.id.cover_image)
    var filmTitle: TextView = itemView.findViewById(R.id.film_title)
    var filmDate: TextView = itemView.findViewById(R.id.film_date)
    var filmFav: CheckBox = itemView.findViewById(R.id.film_fav)
    var filmRating: TextView = itemView.findViewById(R.id.film_rating)
    var filmPosition: TextView = itemView.findViewById(R.id.film_position)
    fun bind() {
        filmFav.isChecked= filmTitle.text.toString() in FavoriteFilms.favs
    }
    }