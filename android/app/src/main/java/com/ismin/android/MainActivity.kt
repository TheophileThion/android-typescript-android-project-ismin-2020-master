package com.ismin.android

import android.R.layout
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_film.*
import kotlinx.android.synthetic.main.item_film.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),FilmCreator {
    private val TAG = MainActivity::class.simpleName
    private var films = Films()
    private lateinit var filmService: FilmService
    private lateinit var tabLayout: TabLayout
    private lateinit var searchView: SearchView
    private lateinit var adapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabLayout)


        searchView = findViewById(R.id.searchView)
        adapter = ArrayAdapter<Film>(this,android.R.layout.simple_list_item_1, films.getAllFilms())
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)   refreshFragment(query)
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null)   refreshFragment(query)
                return false
            }
        })






        FavoriteFilms.loadFavoriteFilms(this)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://imdb.thionman.cleverapps.io")            .build()

        filmService = retrofit.create(FilmService::class.java)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0)
                {
                    refreshFragment(searchView.query.toString())
                    searchView.visibility = View.VISIBLE
                }
                else
                {
                    searchView.visibility = View.GONE
                    refreshFavFragment()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }




    override fun onResume() {
        super.onResume()
        refreshFragment(searchView.query.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    private fun displayErrorToast(t: Throwable) {
        Toast.makeText(
            applicationContext,
            "Network error ${t.localizedMessage}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun displayList() {
        if(tabLayout.selectedTabPosition!=0) {
            tabLayout.selectTab(tabLayout.getTabAt(0))
            return
        }
        val filmListFragment = FilmListFragment.newInstance(films.getAllFilms(),FavoriteFilms.favs)

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_lyt_container, filmListFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun displayFavList() {
        val favFilmsFragment = FavFilmsFragment.newInstance(films.getAllFilms(),FavoriteFilms.favs)

        supportFragmentManager.beginTransaction()
                .replace(R.id.a_main_lyt_container, favFilmsFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }



    private fun emptyFragment(){
        val filmListFragment = FilmListFragment.newInstance(listOf<Film>(),FavoriteFilms.favs)

        supportFragmentManager.beginTransaction()
            .replace(R.id.a_main_lyt_container, filmListFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun refreshFragment(query : String){
        films= Films()
        emptyFragment()
        filmService.getFilms(search = query).enqueue(object : Callback<ArrayList<Film>> {
            override fun onResponse(
                    call: Call<ArrayList<Film>>,
                    response: Response<ArrayList<Film>>
            ) {
                val allFilms = response.body()
                allFilms?.forEach {
                    films.addFilm(it)
                }
                displayList()
            }

            override fun onFailure(call: Call<ArrayList<Film>>, t: Throwable) {
                displayErrorToast(t)
            }
        })
        displayList()
    }

    private fun refreshFavFragment(){
        films= Films()
        emptyFragment()
        filmService.getFilms().enqueue(object : Callback<ArrayList<Film>> {
            override fun onResponse(
                    call: Call<ArrayList<Film>>,
                    response: Response<ArrayList<Film>>
            ) {
                val allFilms = response.body()
                allFilms?.forEach {
                    if (it.title in FavoriteFilms.favs) films.addFilm(it)
                }
                displayFavList()
            }

            override fun onFailure(call: Call<ArrayList<Film>>, t: Throwable) {
                displayErrorToast(t)
            }
        })
        displayFavList()
    }



    fun goToCreation(view: View) {
        val createFilmFragment = CreateFilmFragment()
        createFilmFragment.view?.bringToFront()
        supportFragmentManager.beginTransaction()
            .add(R.id.a_main_lyt_container, createFilmFragment)
            .addToBackStack("createFilmFragment")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()


        a_main_btn_creation.visibility = View.GONE
    }

    fun setFavorite(view: View) {
        val checkBox = view as CheckBox
        if (checkBox.isChecked) {
            FavoriteFilms.favs.add((view.parent as View).findViewById<TextView>(R.id.film_title).text.toString())
        } else {
            FavoriteFilms.favs.remove((view.parent as View).findViewById<TextView>(R.id.film_title).text.toString())
        }
        FavoriteFilms.saveFavoriteFilms(this)
    }

    fun goToFilm(view: View) {
        val filmTitle = view.film_title.text.toString()
        val intent = Intent(this, FilmPage::class.java).apply {
            putExtra("film", filmTitle)
        }
        emptyFragment()
        startActivity(intent)
    }

    fun goToAboutMenu(item: MenuItem) {
        emptyFragment()
        val intent = Intent(this, AboutPage::class.java)
        this.startActivity(intent)
    }


    override fun onFilmCreated(film: Film) {
        filmService.createFilm(film).enqueue {
            onResponse = {
                //films.addFilm(it.body()!!)
                refreshFragment(searchView.query.toString())
                a_main_btn_creation.visibility = View.VISIBLE
            }
            onFailure = {
                if (it != null) {
                    displayErrorToast(it)
                }
            }
        }
    }

    override fun closeCreateFragment() {
        displayList();
        a_main_btn_creation.visibility = View.VISIBLE
    }

}