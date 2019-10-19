package com.project.movieapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.movieapp.adapter.MovieListAdapter
import com.project.movieapp.adapter.RealmMovieListAdapter
import com.project.movieapp.beans.ListResponseBean
import com.project.movieapp.beans.MovieDetailsBean
import com.project.movieapp.beans.ResultBean
import com.project.movieapp.extensions.isNetworkConnected
import com.project.movieapp.extensions.longToast
import com.project.movieapp.extensions.shortToast
import com.project.movieapp.web.WebClient
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val listResults: ArrayList<ResultBean> = arrayListOf()

    private var currentPage: Int = 1
    private val realmInstance: Realm by lazy { Realm.getDefaultInstance() }

    private var selectedOption: String = "popular"

    override fun onStart() {
        if (selectedOption == "favorite") {
            getFavoriteMovies()
        }
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        supportActionBar?.title = null
        //todo implement programatically span count based on resolution
//        val spanCount: Int = when (this.displayMetrics().widthPixels) {
//            else -> 2
//        }
        //todo implement observable to check internet connection and enable only favorites


        list_recyclerview.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)

            if (this@MainActivity.isNetworkConnected())
                getPopularMovies(currentPage)
            else {
                getFavoriteMovies()
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    val recyclerLayout: GridLayoutManager =
                        recyclerView.layoutManager as GridLayoutManager
                    if (recyclerLayout.findLastVisibleItemPosition() == recyclerLayout.itemCount - 1) {
                        when (selectedOption) {
                            "popular" -> getPopularMovies(++currentPage)
                            "top" -> getTopMovies(++currentPage)
                            //todo search movies pagination
                        }
                    }
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorites_menu, menu)
        when (selectedOption) {
            "favorite" -> menu?.getItem(2)?.setIcon(R.drawable.menu_favorite_highlight)
            "popular" -> menu?.getItem(1)?.setIcon(R.drawable.menu_hot_highlight)
            "top" -> menu?.getItem(0)?.setIcon(R.drawable.menu_top_highlight)
//            "search" -> menu?.getItem(3)?.setIcon(R.drawable.menu_search_highlight)
            else -> return true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.top_menu_button -> {
                if (this.isNetworkConnected()) {
                    currentPage = 1
                    getTopMovies(currentPage)
                } else {
                    this.longToast("To view top movies connect to the internet")
                }
            }
            R.id.popular_menu_button -> {
                if (this.isNetworkConnected()) {
                    currentPage = 1
                    getPopularMovies(currentPage)
                } else {
                    this.longToast("To view popular movies connect to the internet")
                }
            }
            R.id.search_menu_button -> {
                //todo search button
                this.shortToast("Feature yet to be implemented")
                selectedOption = "search"
                supportActionBar?.subtitle = "search movies"
                invalidateOptionsMenu()
            }
            R.id.favorite_menu_button -> {
                getFavoriteMovies()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun getFavoriteMovies() {
        selectedOption = "favorite"
        supportActionBar?.subtitle = "Favorite movies"
        invalidateOptionsMenu()

        val result: RealmResults<MovieDetailsBean> =
            realmInstance.where<MovieDetailsBean>().findAll()
        if (result.size > 0)
            list_recyclerview.adapter = RealmMovieListAdapter(this, result)
        else {
            list_recyclerview.adapter = null
            shortToast("No movie favorited")
        }
    }

    override fun onDestroy() {
        realmInstance.close()
        super.onDestroy()
    }

    private fun getTopMovies(page: Int) {
        supportActionBar?.subtitle = "Top rated movies"
        selectedOption = "top"
        invalidateOptionsMenu()
        WebClient().movieService().getTop(page)
            .enqueue(object : Callback<ListResponseBean> {
                override fun onResponse(
                    call: Call<ListResponseBean>,
                    popularResponse: Response<ListResponseBean>
                ) {
                    with(popularResponse) {
                        if (isSuccessful) {
                            body()?.run {
                                loadPages(results, page)
                            }
                        } else {
                            this@MainActivity.longToast(message())
                        }
                    }
                }

                override fun onFailure(call: Call<ListResponseBean>, t: Throwable) {
                    this@MainActivity.longToast(t.message ?: "unidentified error")
                }
            })
    }


    fun loadPages(movies: List<ResultBean>, page: Int) {

        when {
            page == 1 -> {
                listResults.clear()
                listResults.addAll(movies)
                list_recyclerview.adapter = MovieListAdapter(this@MainActivity, listResults)
            }
            //api version 3 only accepts 500 pages
            page < 501 -> {
                listResults.addAll(movies)
                list_recyclerview.adapter?.notifyDataSetChanged()
            }
            else -> this.shortToast("Reached list Limit")
        }
    }


    private fun getPopularMovies(page: Int) {
        supportActionBar?.subtitle = "Popular movies"
        selectedOption = "popular"
        invalidateOptionsMenu()
        WebClient().movieService().getPopular(page)
            .enqueue(object : Callback<ListResponseBean> {
                override fun onResponse(
                    call: Call<ListResponseBean>,
                    popularResponse: Response<ListResponseBean>
                ) {
                    with(popularResponse) {
                        if (isSuccessful)
                            body()?.run { loadPages(results, page) }
                        else
                            this@MainActivity.longToast(message())

                    }
                }

                override fun onFailure(call: Call<ListResponseBean>, t: Throwable) {
                    this@MainActivity.longToast(t.message ?: "unidentified error")
                }
            })
    }
}
