package com.ismin.android

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager


private const val ARG_FILMS = "ARG_FILMS"
private const val ARG_FAV = "ARG_FAV"


class FavFilmsFragment : Fragment() {
    private lateinit var films: ArrayList<Film>
    private lateinit var fav_film:ArrayList<String>
    private lateinit var rcvFilms: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            films = it.getSerializable(ARG_FILMS) as ArrayList<Film>
            fav_film=it.getSerializable(ARG_FAV) as ArrayList<String>
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_film_list, container, false)

        this.rcvFilms = rootView.findViewById(R.id.film_list_rcv_films)
        this.rcvFilms.adapter = FilmAdapter(films,fav_film,this.context as Context)
        val flexBoxLayoutManager = FlexboxLayoutManager(context,FlexDirection.COLUMN)
        val gridLayoutManager = GridLayoutManager(context,1,GridLayoutManager.HORIZONTAL,true)
        this.rcvFilms.layoutManager = gridLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context,gridLayoutManager.orientation)
        this.rcvFilms.addItemDecoration(dividerItemDecoration)

        return rootView;
    }

    companion object {
        @JvmStatic
        fun newInstance(films: List<Film>,fav_film:List<String>) =
                FavFilmsFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_FILMS, ArrayList(films))
                        putSerializable(ARG_FAV, ArrayList(fav_film))
                    }
                }
    }
}