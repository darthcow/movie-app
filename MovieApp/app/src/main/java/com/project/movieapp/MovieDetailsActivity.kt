package com.project.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.project.movieapp.beans.MovieDetailsBean
import com.project.movieapp.extensions.loadUrl
import com.project.movieapp.extensions.longToast
import com.project.movieapp.extensions.shortToast
import com.project.movieapp.web.WebClient
import com.project.movieapp.web.WebClient.URLConstants.APIKEY
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//todo create and implement interface to necessary methods
class MovieDetailsActivity : AppCompatActivity() {

    private var isFavorite: Boolean = false
    private val result by lazy {
        realmInstance.where(MovieDetailsBean::class.java)
            .equalTo("id", movieId).findFirst()
    }
    private var movieDetails: MovieDetailsBean? = null
    private val realmInstance: Realm by lazy { Realm.getDefaultInstance() }
    private val movieId: Int? by lazy { intent.extras?.getInt("movieId", 0) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        movieId?.let {
            if (result?.id != it)
                getFromApi(it)
            else{
               movieDetails = result
                movieDetails?.let { it1 -> loadData(it1) }
            }
        }
        supportActionBar?.hide()
        details_favorite_fab.setOnClickListener { favoriteClick() }


    }

    private fun favoriteClick() {
        movieDetails?.let {
            if (isFavorite)
                removeFromRealm()
            else
                saveToRealm(it)
        }
    }

    private fun removeFromRealm() {
        realmInstance.beginTransaction()
        if (realmInstance.where(MovieDetailsBean::class.java)
                .equalTo("id", movieId).findAll().deleteAllFromRealm()
        ) {
            details_favorite_fab.setImageResource(R.drawable.ic_favorite_border_black_64dp)
            isFavorite = false
        }
        realmInstance.commitTransaction()


    }


    override fun onStop() {
        realmInstance.close()
        super.onStop()
    }

    private fun saveToRealm(movie: MovieDetailsBean) {
        realmInstance.beginTransaction()
        realmInstance.executeTransactionAsync({ bgRealm ->
            bgRealm.copyToRealm(movie)
        }, {
            // Transaction was a success.
            details_favorite_fab.setImageResource(R.drawable.ic_favorite_black_64dp)
            isFavorite = true
        }, {
            // Transaction failed and was automatically canceled.
            it.message?.let { it1 -> this.longToast(it1) }
        })
        realmInstance.commitTransaction()
    }


    private fun getFromApi(movieId: Int) {
        WebClient().movieService().getMovieDetails(movieId, APIKEY)
            .enqueue(object : Callback<MovieDetailsBean> {
                override fun onFailure(call: Call<MovieDetailsBean>, t: Throwable) {
                    this@MovieDetailsActivity.shortToast(t.message ?: "unidentified error")
                }

                override fun onResponse(
                    call: Call<MovieDetailsBean>,
                    response: Response<MovieDetailsBean>
                ) {
                    with(response) {
                        if (isSuccessful) {
                            body()?.run {
                                loadData(this)
                                movieDetails = this
                                checkFavorite()
                            }
                        } else {
                            this@MovieDetailsActivity.longToast(message())
                        }
                    }
                }
            })
    }

    private fun checkFavorite() {
        return if (result?.id == movieDetails?.id) {
            details_favorite_fab.setImageResource(R.drawable.ic_favorite_black_64dp)
            isFavorite = true
        } else {
            details_favorite_fab.setImageResource(R.drawable.ic_favorite_border_black_64dp)
            isFavorite = false
        }
    }

    private fun loadData(movieDetailsBean: MovieDetailsBean) {
        with(movieDetailsBean) {
            details_backdrop.loadUrl(WebClient.URLConstants.IMAGEURL + backdrop_path)
            details_title.text = "$title"
            details_length.text = "Length: $runtime minutes"
            //tagline
            if (tagline.isNullOrBlank() || tagline.isNullOrEmpty())
                details_tagline.visibility = View.GONE
            else
                details_tagline.text = "'$tagline'"

            details_releasedate.text = "Release: $release_date"
//            todo add tags later(requires another call)
//            details_tags.addTag()
            genres?.let {
                for (genre in it) {
                    details_genres.addTag(genre.name)
                }
            }
            details_overview_body.text = "$overview"

            //rating bar
            details_ratingbar.setIsIndicator(true)
            details_ratingbar.numStars = 5
            details_ratingbar.stepSize = 0.01f

            details_ratingbar.isEnabled = true
            details_ratingbar.rating = vote_average?.let { it.toFloat() / 2 } ?: 0f
            details_ratingbar.setOnTouchListener { _, _ ->
                this@MovieDetailsActivity.longToast("Score: ${vote_average?.let { it / 2 }} | total votes $vote_count")
                false
            }


//todo create adapter to trailers and reviews
            details_trailers_list.adapter
            details_reviews_list.adapter
        }
    }
}
