package com.ismin.android

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import com.google.android.material.transition.FadeThroughProvider
import kotlinx.android.synthetic.main.activity_main.*

class CreateFilmFragment : Fragment() {
    private var activity: FilmCreator? = null;
    private lateinit var edtTitle: EditText
    private lateinit var edtUrl: EditText
    private lateinit var edtSummary: EditText
    private lateinit var edtRating: EditText
    private lateinit var edtAuthors: EditText
    private lateinit var edtCasting: EditText
    private lateinit var edtGenres: EditText
    private lateinit var edtKeywords: EditText
    private lateinit var edtReleasedDate: EditText
    private lateinit var edtRuntime: EditText
    private lateinit var edtCover: EditText
    private lateinit var edtMembers: EditText
    private lateinit var edtSize: EditText
    private lateinit var edtDateAdded: EditText

    
    private lateinit var card: CardView
    private lateinit var rootLayout: ViewGroup
    private lateinit var blackView: View






    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_create_film, container, false)

        rootLayout = rootView.findViewById(R.id.f_create_film_root_layout)

        edtTitle = rootView.findViewById(R.id.f_create_film_edt_title)
        edtUrl = rootView.findViewById(R.id.f_create_film_edt_url)
        edtSummary = rootView.findViewById(R.id.f_create_film_edt_summary)
        edtRating = rootView.findViewById(R.id.f_create_film_edt_rating)
        edtAuthors = rootView.findViewById(R.id.f_create_film_edt_author)
        edtCasting = rootView.findViewById(R.id.f_create_film_edt_casting)
        edtGenres = rootView.findViewById(R.id.f_create_film_edt_genre)
        edtKeywords = rootView.findViewById(R.id.f_create_film_edt_keywords)
        edtReleasedDate = rootView.findViewById(R.id.f_create_film_edt_released_date)
        edtRuntime = rootView.findViewById(R.id.f_create_film_edt_runtime)
        edtCover = rootView.findViewById(R.id.f_create_film_edt_cover)
        edtMembers = rootView.findViewById(R.id.f_create_film_edt_members)
        edtSize = rootView.findViewById(R.id.f_create_film_edt_size)
        edtDateAdded = rootView.findViewById(R.id.f_create_film_edt_date_added)


        card = rootView.findViewById(R.id.f_create_film_card)
        blackView = rootView.findViewById(R.id.f_create_film_black_view)

        rootView.setOnClickListener { activity?.closeCreateFragment() }
        rootView.findViewById<Button>(R.id.f_create_film_btn_save).setOnClickListener {
            saveFilm()
        }

        ObjectAnimator.ofFloat(card, "translationY", 300f, 0f)
                .apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 300
                    start()
                }

        FadeThroughProvider().createAppear(rootLayout, card)?.start()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()

        ObjectAnimator.ofFloat(card, "translationY", 0f, 300f)
                .apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 100
                    start()

                }
            .doOnEnd {  activity?.closeCreateFragment()
            }
        FadeThroughProvider().createDisappear(rootLayout, card)?.start()
    }

    override fun onAttach(context: Context) {
        if (context is FilmCreator) {
            activity = context
        }
        super.onAttach(context)
    }

    fun saveFilm() {
        activity?.onFilmCreated(
                Film(
                        edtTitle.text.toString(),
                        edtUrl.text.toString(),
                        edtSummary.text.toString(),
                        edtRating.text.toString().toFloat()/10,
                        edtAuthors.text.toString().split(",").toTypedArray(),
                        edtCasting.text.toString().split(",").toTypedArray(),
                        edtGenres.text.toString().split(",").toTypedArray(),
                        edtKeywords.text.toString().split(",").toTypedArray(),
                        edtReleasedDate.text.toString(),
                        edtRuntime.text.toString().toInt(),
                        edtCover.text.toString(),
                        edtMembers.text.toString().toInt(),
                        edtSize.text.toString().toInt(),
                        edtDateAdded.text.toString()
                )
        )
    }
}

interface FilmCreator {
    fun onFilmCreated(film: Film)
    fun closeCreateFragment()
}