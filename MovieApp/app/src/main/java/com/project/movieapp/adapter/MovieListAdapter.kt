package com.project.movieapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.movieapp.MovieDetailsActivity
import com.project.movieapp.R
import com.project.movieapp.extensions.loadUrl
import com.project.movieapp.beans.ResultBean
import com.project.movieapp.web.WebClient.URLConstants

class MovieListAdapter(private val context: Context, private val items: List<ResultBean>) :
    RecyclerView.Adapter<MovieListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder = MovieListHolder(LayoutInflater.from(context).inflate(R.layout.adapter_movie_list_item, parent, false))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(movieListHolder: MovieListHolder, position: Int) {
        val currentItem = items[position]

        with(currentItem){
            movieListHolder.movieTitle.text = title
            movieListHolder.moviePoster.loadUrl(getPosterPath())
            movieListHolder.itemView.setOnClickListener { openMovieDetails(id)}

        }
    }
    private fun openMovieDetails(id: Int) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", id)
        context.startActivity(intent)
    }
}

class MovieListHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    //fixme refactor names later to reference adapter
    val movieTitle: TextView = itemview.findViewById(R.id.movieTitle)
    val moviePoster: ImageView = itemview.findViewById(R.id.moviePoster)
}