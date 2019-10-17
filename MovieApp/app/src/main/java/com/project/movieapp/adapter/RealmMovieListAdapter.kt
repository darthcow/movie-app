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
import com.project.movieapp.beans.MovieDetailsBean
import com.project.movieapp.beans.ResultBean
import com.project.movieapp.extensions.loadUrl
import com.project.movieapp.web.WebClient
import io.realm.RealmResults

class RealmMovieListAdapter(private val context: Context, private val items: RealmResults<MovieDetailsBean>) :
    RecyclerView.Adapter<RealmMovieListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealmMovieListHolder = RealmMovieListHolder(
        LayoutInflater.from(context).inflate(R.layout.adapter_movie_list_item, parent, false))


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(movieListHolder: RealmMovieListHolder, position: Int) {
        val currentItem = items[position]

        with(currentItem){
            movieListHolder.movieTitle.text = this?.title
            movieListHolder.moviePoster.loadUrl(WebClient.URLConstants.IMAGEURL+this?.poster_path)
            movieListHolder.itemView.setOnClickListener { this?.id?.let { it1 ->
                openMovieDetails(
                    it1
                )
            } }

        }
    }
    private fun openMovieDetails(id: Int) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra("movieId", id)
        context.startActivity(intent)
    }
}

class RealmMovieListHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    //fixme refactor names later to reference adapter
    val movieTitle: TextView = itemview.findViewById(R.id.movieTitle)
    val moviePoster: ImageView = itemview.findViewById(R.id.moviePoster)
}