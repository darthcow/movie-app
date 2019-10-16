package com.project.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.project.movieapp.adapter.MovieListAdapter
import com.project.movieapp.extensions.longToast
import com.project.movieapp.beans.ListResponseBean
import com.project.movieapp.extensions.displayMetrics
import com.project.movieapp.extensions.shortToast
import com.project.movieapp.web.WebClient
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val realmInstance : Realm  by lazy { Realm.getDefaultInstance() }

    private var selectedOption: String = "popular"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        supportActionBar?.title = null
        //todo implement programatically span count based on resolution
        val spanCount: Int = when (this.displayMetrics().widthPixels) {
            else -> 2
        }
        list_recyclerview.apply {
            layoutManager = GridLayoutManager(this@MainActivity, spanCount)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            getPopularMovies()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorites_menu, menu)
        when (selectedOption) {
            "top" -> menu?.getItem(0)?.setIcon(R.drawable.menu_top_highlight)
            "popular" -> menu?.getItem(1)?.setIcon(R.drawable.menu_hot_highlight)
            "favorite" -> menu?.getItem(2)?.setIcon(R.drawable.menu_favorite_highlight)
            "search" -> menu?.getItem(0)?.setIcon(R.drawable.menu_search_highlight)
            else -> return true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.top_menu_button -> getTopMovies()
            R.id.popular_menu_button -> getPopularMovies()
            R.id.search_menu_button -> {
                this.shortToast("Feature yet to be implemented")
                selectedOption = "search"
                supportActionBar?.subtitle = "search movies"
                invalidateOptionsMenu()
            }
            R.id.favorite_menu_button -> {
                //todo implement favorites listing
                selectedOption = "favorite"
                supportActionBar?.subtitle = "Favorite movies"
                list_recyclerview.adapter = null
                invalidateOptionsMenu()

            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun getTopMovies() {
        WebClient().movieService().getTop().enqueue(object : Callback<ListResponseBean> {
            override fun onResponse(
                call: Call<ListResponseBean>,
                popularResponse: Response<ListResponseBean>
            ) {
                with(popularResponse) {
                    if (isSuccessful) {
                        body()?.run {
                            supportActionBar?.subtitle = "Top rated movies"
                            supportActionBar?.displayOptions
                            selectedOption = "top"
                            invalidateOptionsMenu()
                            list_recyclerview.adapter =
                                MovieListAdapter(this@MainActivity, results)
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


    private fun getPopularMovies() {
        WebClient().movieService().getPopular().enqueue(object : Callback<ListResponseBean> {
            override fun onResponse(
                call: Call<ListResponseBean>,
                popularResponse: Response<ListResponseBean>
            ) {
                with(popularResponse) {
                    if (isSuccessful) {
                        body()?.run {
                            supportActionBar?.subtitle = "Popular movies"
                            selectedOption = "popular"
                            invalidateOptionsMenu()
                            list_recyclerview.adapter =
                                MovieListAdapter(this@MainActivity, results)
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
}
