package com.project.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import com.project.movieapp.beans.MovieDetailsBean
import com.project.movieapp.extensions.loadUrl
import com.project.movieapp.extensions.longToast
import com.project.movieapp.extensions.shortToast
import com.project.movieapp.web.WebClient
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsActivity : AppCompatActivity() {
    private val movieId: Int? by lazy { intent.extras?.getInt("movieId", 0) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        movieId?.let { getMovieDetails(it) }
    }


    private fun getMovieDetails(movieId: Int) {
        WebClient().movieService().getMovieDetails(movieId)
            .enqueue(object : Callback<MovieDetailsBean> {
                override fun onFailure(call: Call<MovieDetailsBean>, t: Throwable) {
                    this@MovieDetailsActivity.shortToast(t.message?: "unidentified error")
                }

                override fun onResponse(
                    call: Call<MovieDetailsBean>,
                    response: Response<MovieDetailsBean>
                ) {
                    with(response) {
                        if (isSuccessful) {
                            body()?.run {
                                loadData(this)
                            }
                        } else {
                            this@MovieDetailsActivity.longToast(message())
                        }


                    }
                }
            })
    }

    private fun loadData(movieDetailsBean: MovieDetailsBean) {
        with(movieDetailsBean) {
            details_backdrop.loadUrl(WebClient.URLConstants.IMAGEURL + backdrop_path ?: poster_path)
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
            for (genre in genres) {
                details_genres.addTag(genre.name)
            }
            details_overview_body.text = "$overview"

            //rating bar
            details_ratingbar.setIsIndicator(true)
            details_ratingbar.numStars = 5
            details_ratingbar.stepSize = 0.01f

            details_ratingbar.isEnabled = true
            details_ratingbar.rating = vote_average.toFloat() / 2
            details_ratingbar.setOnTouchListener { _, _ ->
                this@MovieDetailsActivity.longToast("Score: ${vote_average / 2} | total votes $vote_count")
                false
            }




            details_trailers_list.adapter
            details_reviews_list.adapter
        }
    }
}
