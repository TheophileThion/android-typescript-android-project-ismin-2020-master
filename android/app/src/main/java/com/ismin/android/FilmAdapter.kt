package com.ismin.android

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FilmAdapter(private val films: ArrayList<Film>, private val fav_film: ArrayList<String>, private val context:Context) : RecyclerView.Adapter<FilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(row)
    }
    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val (title, url, summary, aggregated_rating, authors, casting,
            genres, keywords, first_release_date, runtime, cover, members,
            size, date_added) = this.films[position]

        holder.filmTitle.text = title
        holder.filmDate.text = first_release_date
        holder.filmRating.text = (aggregated_rating*10).toInt().toString()
        val rating = (aggregated_rating*10).toInt()
        var ratingColor = getColor(context,R.color.ratingGrey)
        if (rating>0){
            ratingColor=getColor(context,R.color.ratingRed)
        }
        if (rating>50) {
            ratingColor = getColor(context,R.color.ratingOrange)
        }
        if (rating>75) {
            ratingColor = getColor(context,R.color.ratingGreen)
        }
        if (rating>90) {
            ratingColor = getColor(context,R.color.ratingBlue)
        }
        holder.filmRating.backgroundTintList = ColorStateList.valueOf(ratingColor)
        holder.filmFav.isChecked=title in fav_film
        Picasso.get().load(cover).resize(400,600).into(holder.coverImage)
        holder.bind()
    }


    override fun getItemCount(): Int {
        return this.films.size
    }

    fun updateItem(filmsToDisplay: List<Film>) {
        films.clear();
        films.addAll(filmsToDisplay)
        notifyDataSetChanged();
    }
}
